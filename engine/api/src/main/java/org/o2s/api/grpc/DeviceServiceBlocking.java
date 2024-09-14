package org.o2s.api.grpc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import o2sgrpc.service.DeviceItem;
import o2sgrpc.service.DeviceRequest;
import o2sgrpc.service.DevicesResponse;
import org.o2s.orm.DataResource;

@ApplicationScoped  // TODO implement blocking stub for grpcclient
public class DeviceServiceBlocking {
    @Inject
    DataResource dataResource;

    public DeviceItem getDevice(Integer deviceId) {
        var device = dataResource.getDevice(deviceId);
        return DeviceItem.newBuilder().setDeviceHost(device.getHost()).setId(deviceId).build();
    }

    public DevicesResponse getDevices(DeviceRequest request) {
        var devices = dataResource.getDevices(request.getEnvId());
        return DevicesResponse.newBuilder().addAllDevices(devices.stream()
                .map(d -> DeviceItem.newBuilder().setDeviceHost(d.getHost()).setId(d.getId()).build()).toList()).build();
    }
}
