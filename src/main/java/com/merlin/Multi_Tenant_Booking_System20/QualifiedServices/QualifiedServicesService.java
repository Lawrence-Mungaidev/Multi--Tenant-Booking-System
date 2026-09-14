package com.merlin.Multi_Tenant_Booking_System20.QualifiedServices;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.Services.ServicesRepository;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileRepository;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QualifiedServicesService {

    private final QualifiedServicesRepository qualifiedServicesRepository;
    private final QualifiedServiceMapper qualifiedServiceMapper;
    private final StaffProfileRepository spRepository;
    private final ServicesRepository servicesRepository;
    private final UserRepository userRepository;

    public QualifiedServiceResponseDto createQualifiedService(QualifiedServiceDto dto){
        StaffProfile staffProfile = spRepository.findById(dto.staffProfileId())
                .orElseThrow(()-> new ResourceNotFound("Staff Profile Not Found"));

        Services services = servicesRepository.findById(dto.servicesId())
                .orElseThrow(()-> new ResourceNotFound("Services Not Found"));

        if(!staffProfile.isAvailable()){
            throw new BusinessRuleException("Staff Profile isn't Available");
        }

        if(!services.isAvailable()) {
            throw new BusinessRuleException("Services isn't Available");
        }

        QualifiedServices qualifiedServices = new QualifiedServices(staffProfile, services);

        var savedQualifiedServices = qualifiedServicesRepository.save(qualifiedServices);

        return qualifiedServiceMapper.toQualifiedServiceResponseDto(savedQualifiedServices);
    }


    public QualifiedServiceResponseDto updateQualifiedService(Long qSid, QualifiedServiceDto dto){
        QualifiedServices qualifiedServices = qualifiedServicesRepository.findById(qSid)
                .orElseThrow(()-> new ResourceNotFound("QualifiedServices Not Found"));

        StaffProfile staffProfile = spRepository.findById(dto.staffProfileId())
                .orElseThrow(()-> new ResourceNotFound("Staff Profile Not Found"));

        if(!staffProfile.isAvailable()){
            throw new BusinessRuleException("Staff Profile isn't Available");
        }

        if(dto.servicesId() != null){
            Services services = servicesRepository.findById(dto.servicesId())
                    .orElseThrow(()-> new ResourceNotFound("Services Not Found"));

            if(!services.isAvailable()) {
                throw new BusinessRuleException("Services isn't Available");
            }

            qualifiedServices.setServices(services);
        }

        var savedQs = qualifiedServicesRepository.save(qualifiedServices);

        return qualifiedServiceMapper.toQualifiedServiceResponseDto(savedQs);
    }

    public void deleteQualifiedService(Long qSid, User authenticatedUser){
        QualifiedServices qualifiedServices = qualifiedServicesRepository.findById(qSid)
                .orElseThrow(()-> new ResourceNotFound("QualifiedServices Not Found"));

        StaffProfile staffProfile = authenticatedUser.getStaffProfile();

        if(!staffProfile.isAvailable()){
            throw new BusinessRuleException("Staff Profile isn't Available");
        }

        if(!staffProfile.getUserId().equals(qualifiedServices.getStaffProfile().getUserId())){
            throw new BusinessRuleException("Sorry you cannot delete this service");
        }

        qualifiedServicesRepository.delete(qualifiedServices);

    }

    public List<QualifiedServiceResponseDto> getAllQualifiedServices(){
        return qualifiedServicesRepository.findAll()
                .stream()
                .map(qualifiedServiceMapper :: toQualifiedServiceResponseDto)
                .toList();
    }
}
