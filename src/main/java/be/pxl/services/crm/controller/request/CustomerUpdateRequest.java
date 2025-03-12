package be.pxl.services.crm.controller.request;

public record CustomerUpdateRequest (
    String firstName,
    String lastName,
    String email,
    String phone,
    String address,
    String city,
    String zipCode){
}
