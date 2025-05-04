package agus.ramdan.cdt.core.trx.service.qrcode;

import agus.ramdan.base.exception.ErrorValidation;
import agus.ramdan.base.exception.ResourceNotFoundException;
import agus.ramdan.base.service.BaseQueryEntityService;
import agus.ramdan.cdt.core.master.persistence.domain.ServiceProduct;
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
            ServiceProduct product = qr.getServiceProduct();
            if (product == null) {
                validations.add(ErrorValidation.New("Product not found", key + "product", qr.getCode()));
            } else {
                if (product.getId() == null) {
                    validations.add(ErrorValidation.New("Product not found", key + "product.id", qr.getCode()));
                } else {
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
                            val customer = qr.getCustomer();
                            if (customer == null) {
                                validations.add(ErrorValidation.New("Customer not found", key + "customer", qr.getCode()));
                            } else {
                                customerId = customer.getId();
                            }

                            val beneficiaryAccount = qr.getBeneficiaryAccount();
                            if (beneficiaryAccount == null) {
                                validations.add(ErrorValidation.New("Beneficiary Account not found", key + "beneficiary_account", qr.getCode()));
                            }else {
                                if(customerId != null &&!customerId.equals(beneficiaryAccount.getCustomer().getId())){
                                    validations.add(ErrorValidation.New("Customer not match with beneficiary", key + "customer.id", qr.getCode()));
                                }
                            }

                            val customerCrew = qr.getUser();
                            if (customerCrew == null) {
                                validations.add(ErrorValidation.New("Customer Crew not found", key + "customer_crew", qr.getCode()));
                            }else {
                                if(customerId != null &&!customerId.equals(customerCrew.getCustomer().getId())){
                                    validations.add(ErrorValidation.New("Customer not match with customer crew", key + "customer_crew.id", qr.getCode()));
                                }
                            }
                        }
                        case COLLECTION -> {
                            val vendor = qr.getVendor();
                            if (vendor == null) {
                                validations.add(ErrorValidation.New("Vendor not found", key + "vendor", qr.getCode()));
                            } else {
                                if (vendor.getId() == null) {
                                    validations.add(ErrorValidation.New("Vendor not found", key + "vendor.id", qr.getCode()));
                                }
                            }
                            val vendorCrew = qr.getVendorCrew();
                            if (vendorCrew == null) {
                                validations.add(ErrorValidation.New("Vendor Crew not found", key + "vendor_crew", qr.getCode()));
                            } else {
                                if (vendorCrew.getId() == null) {
                                    validations.add(ErrorValidation.New("Vendor Crew not found", key + "vendor_crew.id", qr.getCode()));
                                }
                            }
                        }
                    }
                    product.setId(product.getId());
                }
            }
        return qr;
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
