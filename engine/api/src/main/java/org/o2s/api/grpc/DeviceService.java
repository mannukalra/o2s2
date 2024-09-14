package org.o2s.api.grpc;

import java.util.concurrent.atomic.AtomicInteger;

import o2sgrpc.service.Device;
import o2sgrpc.service.DeviceReply;
import o2sgrpc.service.DeviceRequest;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class DeviceService implements Device {

    AtomicInteger counter = new AtomicInteger();

    @Override
    public Uni<DeviceReply> getDevice(DeviceRequest request) {
        int count = counter.incrementAndGet();
        String name = request.getEnvName();
        return Uni.createFrom().item("Hello " + name)
                .map(res -> DeviceReply.newBuilder().setDeviceDetails(res).setCount(count).build());
    }
}
