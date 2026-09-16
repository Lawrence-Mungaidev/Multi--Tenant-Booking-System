package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import com.merlin.Multi_Tenant_Booking_System20.Exceptions.BusinessRuleException;
import com.merlin.Multi_Tenant_Booking_System20.Exceptions.ResourceNotFound;
import com.merlin.Multi_Tenant_Booking_System20.StaffProfile.StaffProfile;
import com.merlin.Multi_Tenant_Booking_System20.User.Role;
import com.merlin.Multi_Tenant_Booking_System20.User.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorResponseDto createVendor(VendorDto dto, User authenticatedUser) {
        if(!authenticatedUser.getRole().equals(Role.OWNER)){
            throw new BusinessRuleException("Only admins can create vendors");
        }


        Vendor vendor = vendorMapper.toVendor(dto);
        vendor.setBusinessOwner(authenticatedUser);

        var savedVendor = vendorRepository.save(vendor);

        return vendorMapper.toVendorResponseDto(savedVendor);
    }

    public VendorResponseDto updateVendor(Long vendorId,VendorDto dto, User authenticatedUser) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()->new ResourceNotFound("Vendor not found"));

        if(!authenticatedUser.getRole().equals(Role.OWNER)){
            throw new BusinessRuleException("Only admins can update vendors");
        }

        if(!vendor.getBusinessOwner().equals(authenticatedUser)){
            throw new BusinessRuleException("You cannot update this vendor");
        }

        if(dto.vendorName() != null){
            vendor.setVendorName(dto.vendorName());
        }
        if(dto.vendorAddress() != null){
            vendor.setVendorAddress(dto.vendorAddress());
        }
        if(dto.vendorEmail() != null){
            vendor.setVendorEmail(dto.vendorEmail());
        }
        if(dto.vendorPhone() != null){
            vendor.setVendorPhone(dto.vendorPhone());
        }
        if(dto.vendorCity() != null){
            vendor.setVendorCity(dto.vendorCity());
        }
        if(dto.openingHours() != null){
            vendor.setOpeningHours(dto.openingHours());
        }
        if(dto.closingHours() != null){
            vendor.setClosingHours(dto.closingHours());
        }
        vendorRepository.save(vendor);
        return vendorMapper.toVendorResponseDto(vendor);
    }

    public List<VendorResponseDto> getAllVendors(User authenticatedUser) {
        if(!authenticatedUser.getRole().equals(Role.ADMIN)){
            throw new BusinessRuleException("You cannot perform this operation");
        }

        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper :: toVendorResponseDto)
                .toList();
    }

    public VendorResponseDto getVendorById(Long vendorId, User authenticatedUser) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()->new ResourceNotFound("Vendor not found"));

        boolean isOwner = authenticatedUser.getRole().equals(Role.OWNER) && authenticatedUser.equals(vendor.getBusinessOwner());

        if(!isOwner){
            throw new BusinessRuleException("You view this vendor");
        }
        return vendorMapper.toVendorResponseDto(vendor);
    }
}
