import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        /*-----------VALUE INITIALISATION-----------*/
        double possibility = 0.1;
        ArrayList<Double> avgDelay = new ArrayList<>();
        ArrayList<Double> throughput = new ArrayList<>();
        ArrayList<Double> PacketLossRate = new ArrayList<>();
        for (int z = 1; z <= 10; z++) {
            possibility = 1.0 * z / 10;
            int totalDelay = 0;
            Random random = new Random();
            float TransmissionPossibility = 0.5f;
            int slot = 1, CreatedPackets = 0, LostPackets = 0, PacketsSent = 0;
            ArrayList<Integer> RegisteredTransmission = new ArrayList<>();//The registered for transmission clients' ids are stored
            ArrayList<Boolean> transmission = new ArrayList<>();/*A boolean for each wavelength to check if a client is using the wavelength index+1 (eg if a client using W1 wants to transmit the Boolean object with index 0 becomes true)*/
            for (int i = 0; i < 4; i++) {
                transmission.add(false);
            }
            ArrayList<Boolean> collision = new ArrayList<>();/*A boolean for each wavelength to check if more than one client using the wavelength index+1 wants to transmit on the same time slot(eg if 2 or more clients using W1 want to transmit on the same time slot the Boolean object with index 0 becomes true)*/
            for (int i = 0; i < 4; i++) {
                collision.add(false);
            }
            ArrayList<pc> clients = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                clients.add(new pc(possibility, (i % 4 + 1), i + 1));
            }

            /*-----------END OF VALUE INITIALISING-----------*/
            /*-----------TIME SLOT OPERATIONS-----------*/
            while (slot < 100001) {
                System.out.println("----------TIME SLOT:" + slot + "----------");
                for (int i = 0; i < 8; i++) {
                    if (clients.get(i).WantToTransmit()) {//Checking if the specific client has a packet in its buffer
                        if (random.nextFloat() <= TransmissionPossibility) {//if so the client tries to transmit with 'TransmissionPossibility' possibility
                            if (transmission.get(clients.get(i).getWavelength() - 1)) {//if another client using the same wavelength already wants to transmit the collision flag is set to true
                                collision.set(clients.get(i).getWavelength() - 1, true);
                            } else {//if no other client using the same wavelength wants to transmit the transmission flag for the equivalent wavelength is set to true and the client's id is registered for transmission
                                transmission.set(clients.get(i).getWavelength() - 1, true);
                                RegisteredTransmission.add(clients.get(i).getId());
                            }
                        }
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (RegisteredTransmission.contains(clients.get(i).getId())) {
                        if (!collision.get(clients.get(i).getWavelength() - 1)) {
                            totalDelay += slot - clients.get(i).transmit();
                            System.out.println("pc id:" + clients.get(i).getId() + "    Transmitting packet");
                            PacketsSent += 1;
                        }
                    }
                }

                RegisteredTransmission.clear();

                for (int i = 0; i < 4; i++) {//re-initialising transmission and collision flags for each wavelength
                    transmission.set(i, false);
                    collision.set(i, false);
                }
                for (int i = 0; i < 8; i++) {//Checking if there are any arrivals on each client
                    if (clients.get(i).Arrival()) {
                        CreatedPackets += 1;
                        if (!clients.get(i).RegisterPacket(slot)) {
                            LostPackets += 1;//Checking if the arrived packet was lost
                        }
                    }
                }
                slot += 1;
            }
            avgDelay.add(1.0 * totalDelay / PacketsSent);
            throughput.add(1.0 * PacketsSent / (slot - 1));
            PacketLossRate.add(1.0 * LostPackets / CreatedPackets);
            System.out.println(CreatedPackets);
            System.out.println(LostPackets);
            System.out.println(PacketsSent);
        }
        System.out.println(avgDelay);
        System.out.println(throughput);
        System.out.println(PacketLossRate);
        /*-----------END OF TIME SLOT OPERATIONS-----------*/
    }

}