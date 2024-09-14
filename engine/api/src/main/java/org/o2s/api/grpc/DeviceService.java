package org.o2s.api.grpc;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.inject.Inject;
import o2sgrpc.service.Device;
import o2sgrpc.service.DeviceItem;
import o2sgrpc.service.DeviceRequest;
import o2sgrpc.service.DevicesResponse;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;

@GrpcService
public class DeviceService implements Device {



    AtomicInteger counter = new AtomicInteger();

    @Override
    public Uni<DeviceItem> getDevice(DeviceRequest request) {
        int count = counter.incrementAndGet();
        Integer id = request.getEnvId();
        return Uni.createFrom().item("Hello " + id)
                .map(res -> DeviceItem.newBuilder().setDeviceHost(res).setId(count).build());
    }

    @Override
    public Uni<DevicesResponse> getDevices(DeviceRequest request) {
//        TODO how to make blocking or async call here
        var device = new org.o2s.orm.entity.Device();
        device.setId(12);
        device.setHost("TestHost");
        var devices = List.of(device);
        return Uni.createFrom().item(
                DevicesResponse.newBuilder().addAllDevices(devices.stream()
                .map(d -> DeviceItem.newBuilder().setDeviceHost(d.getHost()).setId(d.getId()).build()).toList()).build());
    }
}
