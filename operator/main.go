package main

import (
    pb "o2s/operator/o2sgrpc/service"
    "context"
    "google.golang.org/grpc"
    "google.golang.org/grpc/credentials/insecure"
)

// protoc --proto_path=proto proto/*.proto --go_out=. --go-grpc_out=.

func main() {
    conn, err := grpc.Dial(":9000", grpc.WithTransportCredentials(insecure.NewCredentials()))
    if err != nil {
        println("Failed to connect to GRPC server, cause: ", err.Error())
    }
    defer conn.Close()

    deviceClient := pb.NewDeviceClient(conn)
    devicesResponse, err := deviceClient.GetDevices(context.Background(), &pb.DeviceRequest{EnvId: 1})
    if err != nil {
        println("failed to get device details: %v", err)
    }
    println("device details:", devicesResponse.GetDevices()[0].DeviceHost)
}