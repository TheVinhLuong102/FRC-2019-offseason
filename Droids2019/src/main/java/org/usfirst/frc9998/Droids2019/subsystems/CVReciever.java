// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc9998.Droids2019.subsystems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;

import java.util.Base64;

import org.usfirst.frc9998.Droids2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */


public class CVReciever extends Subsystem {

    private static DatagramSocket clientSocket;
    private static java.nio.ByteBuffer buf;
    // private static InetAddress IPAddress;
    private static InetSocketAddress IPAddress;
    private static BufferedReader inFromUser;
    private static DatagramChannel channel;

    public static void initSocket() throws Exception {
        buf = java.nio.ByteBuffer.allocate(1500);
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        // clientSocket = new DatagramSocket();
        IPAddress = new InetSocketAddress("10.99.98.10", 9998);
        // InetAddress.getByName("10.99.98.10")

        channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9998));
        channel.connect(IPAddress);
        channel.configureBlocking(false);
    }


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public CVReciever() {
        try {
            this.initSocket();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void initDefaultCommand() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new GetAllData());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public double getData()
    {
        try {
            buf.clear();
            int len = channel.read(buf);
            if (len > 0) {
                // System.out.println("Socket buf length: "+ len);
                //System.out.println("Decoded: "+Base64.getDecoder().decode(buf.array()));
                //System.out.println("Buf Array: "+buf.array());
                //System.out.println("Buf String: "+new String(buf.array()));
                double dataFinal = Double.parseDouble(new String(buf.array()).split(";")[0]);
                //System.out.println("CVReciever: "+dataFinal);
                return dataFinal;
            } else {
                return(-1.0); // should save last data and return that
            }

        } catch(Exception e) {
            System.out.println("error1 "+e);
            return -1.0;
        }
        



    //    byte[] receiveData = new byte[1024];
    //    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    //     try {
    //         clientSocket.receive(receivePacket);
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    //    String modifiedSentence = new String(receivePacket.getData());
    //    System.out.println("FROM SERVER:" + modifiedSentence);
    //    return(modifiedSentence);
    }

    public static void finish() {
        clientSocket.close();
    }

    public static void sendData(String data) throws IOException {
        String newData = "Send" + System.currentTimeMillis();
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        // int bytesSent = channel.send(buf, IPAddress);
        int bytesSent = channel.write(buf);

        // byte[] sendData = new byte[1024];
        // sendData = data.getBytes();
        // DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        // clientSocket.send(sendPacket);
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

