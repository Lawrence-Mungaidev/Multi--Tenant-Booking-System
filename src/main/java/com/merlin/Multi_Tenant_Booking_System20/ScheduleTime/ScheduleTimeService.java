package com.merlin.Multi_Tenant_Booking_System20.ScheduleTime;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.Services.Services;
import com.merlin.Multi_Tenant_Booking_System20.Services.ServicesRepository;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfileRepository;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTimeService {

    private ScheduleTimeRepository scheduleTimeRepository;
    private ServiceScheduleMapper serviceScheduleMapper;
    private ServicesRepository servicesRepository;
    private StaffProfileRepository staffProfileRepository;

    public ServiceScheduleResponseDto createScheduleTime(ServiceScheduleTimeDto dto, User authenticatedUser) {
        Services services = servicesRepository.findById(dto.ServiceId())
                .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

        boolean userIsAllowed = isOwnerOrManager(services, authenticatedUser);

        if (!userIsAllowed) {
            throw new BusinessRuleException("User is not allowed to create schedule time.");
        }

       if(dto.dayOfWeek().compareTo(services.getVendor().getStartOfWeek()) < 0
               || dto.dayOfWeek().compareTo(services.getVendor().getEndOfWeek()) > 0){
           throw new BusinessRuleException("Invalid day of week");
       }

        if (dto.startTime().isBefore(services.getVendor().getOpeningHours())
                || dto.endTime().isAfter(services.getVendor().getClosingHours())) {
            throw new BusinessRuleException("Schedule time falls outside vendor's operating hours");
        }


        ScheduleTime scheduleTime = new ScheduleTime();
        scheduleTime.setDaysOfWeek(dto.dayOfWeek());
        scheduleTime.setStartTime(dto.startTime());
        scheduleTime.setEndTime(dto.endTime());

        var savedScheduleTime = scheduleTimeRepository.save(scheduleTime);

        return serviceScheduleMapper.toServiceScheduleResponseDto(savedScheduleTime);

    }

    private boolean isOwnerOrManager(Services services, User authenticatedUser) {

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

        return true;
    }

    public ServiceScheduleResponseDto updateScheduleTime(ServiceScheduleTimeDto dto, User authenticatedUser, Long scheduleTimeId) {
        ScheduleTime scheduleTime = scheduleTimeRepository.findById(scheduleTimeId)
                .orElseThrow(()-> new ResourceNotFound("Schedule Time Not Found"));

        Services services = servicesRepository.findById(dto.ServiceId())
                .orElseThrow(()-> new ResourceNotFound("Service Not Found"));


        boolean userIsAllowed = isOwnerOrManager(services, authenticatedUser);

        if (!userIsAllowed) {
            throw new BusinessRuleException("User is not allowed to update schedule time.");
        }

        scheduleTime.setServices(services);

        if(dto.dayOfWeek() !=null){
            scheduleTime.setDaysOfWeek(dto.dayOfWeek());
        }
        if(dto.startTime() != null){
            scheduleTime.setStartTime(dto.startTime());
        }
        if(dto.endTime() != null){
            scheduleTime.setEndTime(dto.endTime());
        }
        var savedScheduleTime = scheduleTimeRepository.save(scheduleTime);

        return serviceScheduleMapper.toServiceScheduleResponseDto(savedScheduleTime);

    }

    public void activateScheduleTime(Long scheduleId, User authenticatedUser) {
        ScheduleTime scheduleTime = scheduleTimeRepository.findById(scheduleId)
                .orElseThrow(()-> new ResourceNotFound("Schedule Time Not Found"));

        Services services = servicesRepository.findById(scheduleTime.getServices().getId())
                .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

        boolean userIsAllowed = isOwnerOrManager(services, authenticatedUser);

        if (!userIsAllowed) {
            throw new BusinessRuleException("User is not allowed to activate schedule time.");
        }

        if (scheduleTime.isActive()) {
            throw new BusinessRuleException("Schedule time is already active.");
        }
        scheduleTime.setActive(true);

        scheduleTimeRepository.save(scheduleTime);
    }

    public void deactivateScheduleTime(Long scheduleId, User authenticatedUser) {
        ScheduleTime scheduleTime = scheduleTimeRepository.findById(scheduleId)
                .orElseThrow(()-> new ResourceNotFound("Schedule Time Not Found"));

        Services services = servicesRepository.findById(scheduleTime.getServices().getId())
                .orElseThrow(()-> new ResourceNotFound("Service Not Found"));

        boolean userIsAllowed = isOwnerOrManager(services, authenticatedUser);

        if (!userIsAllowed) {
            throw new BusinessRuleException("User is not allowed to activate schedule time.");
        }
        if (!scheduleTime.isActive()) {
            throw new BusinessRuleException("Schedule time is already active.");
        }
        scheduleTime.setActive(false);

        scheduleTimeRepository.save(scheduleTime);
    }



}
