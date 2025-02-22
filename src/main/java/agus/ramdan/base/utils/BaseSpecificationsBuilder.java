package agus.ramdan.base.utils;

import graphql.com.google.common.base.Joiner;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BaseSpecificationsBuilder<U> {

    private final List<SpecSearchCriteria> params;

    public BaseSpecificationsBuilder() {
        this.params = new ArrayList<>();
    }

    public final BaseSpecificationsBuilder<U> withSearch(String search) {
        String operationSetExper = Joiner.on("|")
                .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            this.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        return this;
    }

    public final BaseSpecificationsBuilder<U> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final BaseSpecificationsBuilder<U> ids_in(final Collection value) {
        params.add(new SpecSearchCriteria(null, "id", SearchOperation.IN, value));
        return this;
    }

    public final BaseSpecificationsBuilder<U> with(final String precedenceIndicator, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) // the operation may be complex operation
            {
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(precedenceIndicator, key, op, value));
        }
        return this;
    }

    public Specification<U> build(Function<SpecSearchCriteria, Specification<U>> converter) {

        if (params.size() == 0) {
            return null;
        }

        final List<Specification<U>> specs = params.stream()
                .map(converter)
                .collect(Collectors.toCollection(ArrayList::new));

        Specification<U> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specs.get(idx))
                    : Specification.where(result)
                    .and(specs.get(idx));
        }

        return result;
    }

    public Specification<U> build(Deque<?> postFixedExprStack, Function<SpecSearchCriteria, Specification<U>> converter) {

        Deque<Specification<U>> specStack = new LinkedList<>();

        Collections.reverse((List<?>) postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
            Object mayBeOperand = postFixedExprStack.pop();

            if (!(mayBeOperand instanceof String)) {
                specStack.push(converter.apply((SpecSearchCriteria) mayBeOperand));
            } else {
                Specification<U> operand1 = specStack.pop();
                Specification<U> operand2 = specStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR))
                    specStack.push(Specification.where(operand1)
                            .and(operand2));
                else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR))
                    specStack.push(Specification.where(operand1)
                            .or(operand2));
            }

        }
        return specStack.pop();

    }

}