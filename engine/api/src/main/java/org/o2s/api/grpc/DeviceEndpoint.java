package org.o2s.api.grpc;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import o2sgrpc.service.*;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

@Path("/grpc/device")
public class DeviceEndpoint {
    @GrpcClient("grpcc")
    DeviceGrpc.DeviceBlockingStub blockingDeviceService;

    @GrpcClient("grpcc")
    Device deviceService;

    @Inject
    DeviceServiceBlocking deviceServiceBlocking;

    @GET
    @Path("/blocking/{envId}")
    public String helloBlocking(String envId) {
        DeviceItem item = blockingDeviceService.getDevice(DeviceRequest.newBuilder().setEnvId(Integer.parseInt(envId)).build());
        return generateResponse(item);

    }

    @GET
    @Path("/mutiny/{envId}")
    public Uni<String> helloMutiny(String envId) {
        return deviceService.getDevice(DeviceRequest.newBuilder().setEnvId(Integer.parseInt(envId)).build())
                .onItem().transform((reply) -> generateResponse(reply));
    }

    @GET
    @Path("/device/{deviceId}")
    public String getDevice(String deviceId) {
        return generateResponse(deviceServiceBlocking.getDevice(Integer.parseInt(deviceId)));
    }

    @GET
    @Path("/devices/{envId}")
    public String helloMutinyAll(String envId) {
        return generateResponse(deviceServiceBlocking.getDevices(DeviceRequest.newBuilder().setEnvId(Integer.parseInt(envId)).build()));
    }

    public String generateResponse(DeviceItem item) {
        return String.format("%s! DeviceService has been called %d number of times.", item.getDeviceHost(), item.getId());
    }

    public String generateResponse(DevicesResponse items) {
        var result = new StringBuilder();
        items.getDevicesList().forEach(deviceItem -> result.append(
                String.format("%s! DeviceService has been called %d number of times.\n", deviceItem.getDeviceHost(), deviceItem.getId())));
        return result.toString();
    }
}
