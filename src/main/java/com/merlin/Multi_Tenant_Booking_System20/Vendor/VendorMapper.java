package com.merlin.Multi_Tenant_Booking_System20.Vendor;

import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public Vendor toVendor(VendorDto dto) {
        Vendor vendor = new Vendor();
        vendor.setVendorName(dto.vendorName());
        vendor.setVendorEmail(dto.vendorEmail());
        vendor.setVendorPhone(dto.vendorPhone());
        vendor.setVendorCity(dto.vendorCity());
        vendor.setMaxBookingDays(dto.maxBookingDays());
        vendor.setOpeningHours(dto.openingHours());
        vendor.setClosingHours(dto.closingHours());
        vendor.setStartOfWeek(dto.startOfWeek());
        vendor.setEndOfWeek(dto.endOfWeek());

        return vendor;
    }

    public VendorResponseDto toVendorResponseDto(Vendor vendor) {
        return  new VendorResponseDto(vendor.getVendorId(), )
    }
}
