package com.merlin.Multi_Tenant_Booking_System20.Services;

import com.merlin.Multi_Tenant_Booking_System20.Category.Category;
import com.merlin.Multi_Tenant_Booking_System20.Category.CategoryRepository;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileRepository;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import com.merlin.Multi_Tenant_Booking_System20.Vendor.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;
    private final CategoryRepository categoryRepository;
    private final StaffProfileRepository staffProfileRepository;

   public ServiceResponseDto createService(ServiceDto dto){
       Category category = categoryRepository.findById(dto.category())
               .orElseThrow(()-> new RuntimeException("Category Not Found"));

       Services services = serviceMapper.toService(dto);
       services.setCategory(category);

       var savedServices = servicesRepository.save(services);

       return  serviceMapper.toserviceResponseDto(savedServices);

   }

   public ServiceResponseDto updateService(Long ServiceId, ServiceDto dto, User authenticatedUser){

       Services services = servicesRepository.findById(ServiceId)
               .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

       boolean isOwner = services.getVendor().getBusinessOwner().equals(authenticatedUser);

       boolean isManagerAtThisVendor = false;
       if(authenticatedUser.getRole().equals(Role.MANAGER)){
           StaffProfile activeProfile = staffProfileRepository.findById(authenticatedUser.getUserId())
                   .orElseThrow(()-> new ResourceNotFound("Staff Profile Not Found"));

           isManagerAtThisVendor =activeProfile!=null && activeProfile.getVendor().equals(services.getVendor());
       }

       if(!(isOwner || isManagerAtThisVendor)){
           throw new BusinessRuleException("You cannot update this service");
       }



       if(!services.getVendor().getBusinessOwner().equals(authenticatedUser) ){
           throw new BusinessRuleException("You cant edit this service");
       }

       if(dto.name() != null){
           services.setName(dto.name());
       }
       if(dto.description() != null){
           services.setDescription(dto.description());
       }
       if(dto.category() != null){
           Category category = categoryRepository.findById(dto.category())
                   .orElseThrow(()-> new ResourceNotFound("Category Not Found"));
           services.setCategory(category);
       }
       if(dto.price() != null){
           services.setPrice(dto.price());
       }
       if(dto.serviceType() != null){
           services.setServiceType(dto.serviceType());
       }
       if(dto.duration() != 0){
           services.setDuration(dto.duration());

       }
       servicesRepository.save(services);
       return  serviceMapper.toserviceResponseDto(services);

   }

   public List<ServiceResponseDto> findAllServices(User authenticatedUser){
       Vendor vendor = authenticatedUser.getVendor();

       return servicesRepository.findAllServicesByVendor(vendor)
               .stream()
               .map(serviceMapper :: toserviceResponseDto)
               .toList();
   }

  public void enableService(Long ServiceId, User authenticatedUser){
      Services services = servicesRepository.findById(ServiceId)
              .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

      boolean isOwner = services.getVendor().getBusinessOwner().equals(authenticatedUser);

      boolean isManagerAtThisVendor = false;
      if(authenticatedUser.getRole().equals(Role.MANAGER)){
          StaffProfile activeProfile = staffProfileRepository.findById(authenticatedUser.getUserId())
                  .orElseThrow(()-> new ResourceNotFound("Staff Profile Not Found"));

          isManagerAtThisVendor =activeProfile!=null && activeProfile.getVendor().equals(services.getVendor());
      }

      if(!(isOwner || isManagerAtThisVendor)){
          throw new BusinessRuleException("You cannot enable this service");
      }

      if(services.isAvailable()){
          throw new BusinessRuleException("This service is already available");
      }else {
          services.setAvailable(true);
      }
      servicesRepository.save(services);

  }

    public void disableService(Long ServiceId, User authenticatedUser){
        Services services = servicesRepository.findById(ServiceId)
                .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

        boolean isOwner = services.getVendor().getBusinessOwner().equals(authenticatedUser);

        boolean isManagerAtThisVendor = false;
        if(authenticatedUser.getRole().equals(Role.MANAGER)){
            StaffProfile activeProfile = staffProfileRepository.findById(authenticatedUser.getUserId())
                    .orElseThrow(()-> new ResourceNotFound("Staff Profile Not Found"));

            isManagerAtThisVendor =activeProfile!=null && activeProfile.getVendor().equals(services.getVendor());
        }

        if(!(isOwner || isManagerAtThisVendor)){
            throw new BusinessRuleException("You cannot enable this service");
        }

        if(!services.isAvailable()){
            throw new BusinessRuleException("This service is already disabled");
        }else {
            services.setAvailable(false);
        }
        servicesRepository.save(services);

    }






}
