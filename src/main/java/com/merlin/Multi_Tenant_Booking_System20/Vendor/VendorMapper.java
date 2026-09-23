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
        vendor.setClosingHour(dto.closingHours());
        vendor.setStartOfWeek(dto.startOfWeek());
        vendor.setEndOfWeek(dto.endOfWeek());

        return vendor;
    }

    public VendorResponseDto toVendorResponseDto(Vendor vendor) {
        return  new VendorResponseDto(vendor.getVendorId(), vendor.getImageURL(), vendor.getVendorAddress(), vendor.getVendorEmail(), vendor.getVendorPhone(), vendor.getVendorCity(), vendor.getVendorCity(), vendor.getStartOfWeek(), vendor.getEndOfWeek(),vendor.getOpeningHours(), vendor.getClosingHour());
    }
}
