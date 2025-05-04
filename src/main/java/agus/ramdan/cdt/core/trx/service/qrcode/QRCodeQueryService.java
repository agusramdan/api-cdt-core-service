package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.base.utils.EntityFallbackFactory;
import agus.ramdan.cdt.core.master.persistence.domain.*;
import agus.ramdan.cdt.core.trx.controller.dto.qrcode.QRCodeQueryDTO;
import agus.ramdan.cdt.core.trx.mapper.QRCodeMapper;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCode;
import agus.ramdan.cdt.core.trx.persistence.domain.QRCodeType;
import agus.ramdan.cdt.core.trx.persistence.repository.QRCodeRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class QRCodeQueryService implements
        BaseQueryEntityService<QRCode, UUID, QRCodeQueryDTO, String> {

    private final QRCodeRepository repository;
    private final QRCodeMapper mapper;

    @Override
    public UUID convertId(String uuid) {
        return UUID.fromString(uuid);
    }

    @Override
    public QRCodeQueryDTO convertOne(QRCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    @Override
    public QRCodeQueryDTO convert(QRCode entity) {
        return mapper.entityToQueryDto(entity);
    }

    public QRCodeQueryDTO getQRCodeByCode(String code) {
        return findByCode(code)
                .map(mapper::entityToQueryDto)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
    }

    public QRCode chekValidateQRCode(@NotNull QRCode qr, @NotNull final List<ErrorValidation> validations, String key) {
            val code = qr.getCode();
            if (StringUtils.hasText(code)) {
                 key= key+ ".";
            }
            if (code == null) {
                validations.add(ErrorValidation.New("QRCode not found", key + "code", qr.getCode()));
            }
            val type = qr.getType();
            if (type == null) {
                validations.add(ErrorValidation.New("QRCode type not found", key + "type", qr.getCode()));
            }
            ServiceProduct product = EntityFallbackFactory.ensureNotLazy(validations,"Product not found",key + "product",qr::getServiceProduct);
            if (product != null) {
                    switch (product.getQrRuleConfig()) {
                        case SINGLE_USAGE -> {
                            if(!QRCodeType.SINGLE_TRX_USE.equals(type)){
                                validations.add(ErrorValidation.New("QRCode type not match with product", key + "type", qr.getCode()));
                            }
                        }
                        case MULTIPLE_USAGE -> {
                            if(!QRCodeType.MULTIPLE_TRX_USE.equals(type)){
                                validations.add(ErrorValidation.New("QRCode type not match with product", key + "type", qr.getCode()));
                            }
                        }
                    }
                    switch (product.getServiceRuleConfig()) {
                        case DEPOSIT -> {
                            UUID customerId = null;
                            Customer customer = EntityFallbackFactory.ensureNotLazy(validations,"Customer not found",key + "customer",qr::getCustomer);
                            if (customer != null) {
                                customerId = customer.getId();
                            }else {
                                validations.add(ErrorValidation.New("Customer not found", key + "customer", qr.getCode()));
                            }
                            BeneficiaryAccount beneficiaryAccount = EntityFallbackFactory.ensureNotLazy(validations,"Beneficiary Account not found",key + "beneficiary_account",qr::getBeneficiaryAccount);
                            if (beneficiaryAccount != null) {
                                Customer cus =EntityFallbackFactory.ensureNotLazy(validations,"BeneficiaryAccount Customer not found",key + "customer",beneficiaryAccount::getCustomer);
                                if(customerId != null && cus!=null && !customerId.equals(cus.getId())){
                                    validations.add(ErrorValidation.New("Customer not match with beneficiary", key + "customer.id", qr.getCode()));
                                }
                                Bank bank = EntityFallbackFactory.ensureNotLazy(validations,"BeneficiaryAccount Bank not found",key + "beneficiary_account.bank",beneficiaryAccount::getBank);
                                if (bank == null) {
                                    validations.add(ErrorValidation.New("BeneficiaryAccount Bank not found", key + "beneficiary_account.bank", qr.getCode()));
                                }
                            }else {
                                validations.add(ErrorValidation.New("Beneficiary Account not found", key + "beneficiary_account", qr.getCode()));
                            }
                            CustomerCrew customerCrew = EntityFallbackFactory.ensureNotLazy(validations,"QR Code User not found",key + "user",qr::getUser) ;
                            if (customerCrew != null) {
                                Customer crew = EntityFallbackFactory.ensureNotLazy(validations,"Customer Crew not found",key + "user.customer",customerCrew::getCustomer);
                                if(customerId != null && crew != null &&!customerId.equals(crew.getId())){
                                    validations.add(ErrorValidation.New("Customer not match with customer crew", key + "user.customer.id", qr.getCode()));
                                }
                            }else {
                                validations.add(ErrorValidation.New("Customer Crew not found", key + "user", qr.getCode()));
                            }
                        }
                        case COLLECTION -> {
                            Vendor vendor = EntityFallbackFactory.ensureNotLazy(validations,"QR Code User not found",key + "vendor",qr::getVendor);
                            UUID vendorId = null;
                            if (vendor != null) {
                                vendorId = vendor.getId();
                            }
                            VendorCrew vendorCrew = EntityFallbackFactory.ensureNotLazy(validations,"QR Code User not found",key + "vendor_crew",qr::getVendorCrew);
                            if (vendorCrew != null) {
                                Vendor crew = EntityFallbackFactory.ensureNotLazy(validations,"Vendor Crew not found",key + "vendor_crew.vendor",vendorCrew::getVendor);
                                if (vendorId!=null && crew!=null && !vendorId.equals(crew.getId())) {
                                    validations.add(ErrorValidation.New("Vendor not match with Crew", key + "vendor_crew.id", qr.getCode()));
                                }
                            }
                        }
                }
            }
        return qr;
    }
    public QRCodeQueryDTO validateQrCode(UUID code) {
        QRCode qr= repository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        List<ErrorValidation> validations = new ArrayList<>();
        qr = chekValidateQRCode(qr,validations,"qr");
        BadRequestException.ThrowWhenError("QR Invalid",validations,code);
        return mapper.entityToQueryDto(qr);
    }
    public QRCodeQueryDTO validateQrCode(String code) {
        QRCode qr= repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("QR Code not found"));
        List<ErrorValidation> validations = new ArrayList<>();
        qr = chekValidateQRCode(qr,validations,"qr");
        BadRequestException.ThrowWhenError("QR Invalid",validations,code);
        return mapper.entityToQueryDto(qr);
    }

//    public QRCode getForRelation(final QRCodeDTO dto, @NotNull final List<ErrorValidation> validations, String key) {
//        final String keyField = key==null?"bank":key;
//        QRCode data = null;
//        if (dto != null) {
//            if (dto.getId() != null) {
//                data = repository.findById(convertId(dto.getId())).orElseGet(() -> {
//                    validations.add(ErrorValidation.New("QRCode not found",keyField+".id", dto.getId()));
//                    return null;
//                });
//            } else {
//                data = repository.findByCode(dto.getCode()).orElseGet( () -> {
//                    validations.add(ErrorValidation.New("QRCode not found",keyField+".code", dto.getCode()));
//                    return null;
//                });
//            }
//        }
//        return data;
//    }
}
