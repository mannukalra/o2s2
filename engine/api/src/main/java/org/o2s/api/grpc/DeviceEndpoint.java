package org.o2s.api.grpc;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import o2sgrpc.service.Device;
import o2sgrpc.service.DeviceGrpc;
import o2sgrpc.service.DeviceReply;
import o2sgrpc.service.DeviceRequest;
import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;

@Path("/grpc/device")
public class DeviceEndpoint {
    @GrpcClient("grpcc")
    DeviceGrpc.DeviceBlockingStub blockingDeviceService;

    @GrpcClient("grpcc")
    Device deviceService;

    @GET
    @Path("/blocking/{name}")
    public String helloBlocking(String name) {
        DeviceReply reply = blockingDeviceService.getDevice(DeviceRequest.newBuilder().setEnvName(name).build());
        return generateResponse(reply);

    }

    @GET
    @Path("/mutiny/{name}")
    public Uni<String> helloMutiny(String name) {
        return deviceService.getDevice(DeviceRequest.newBuilder().setEnvName(name).build())
                .onItem().transform((reply) -> generateResponse(reply));
    }

    public String generateResponse(DeviceReply reply) {
        return String.format("%s! DeviceService has been called %d number of times.", reply.getDeviceDetails(), reply.getCount());
    }
}
