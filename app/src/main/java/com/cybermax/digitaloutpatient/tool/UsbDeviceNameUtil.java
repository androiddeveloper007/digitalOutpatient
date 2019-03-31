package com.cybermax.digitaloutpatient.tool;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

public class UsbDeviceNameUtil {

    protected static final int STD_USB_REQUEST_GET_DESCRIPTOR = 0x06;

    protected static final int LIBUSB_DT_STRING = 0x03;
    private final UsbManager mUsbManager;

    public UsbDeviceNameUtil(Context context){
        mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
    }

    public String getUSBName() {
        String strusbName = null;
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        if (deviceList.size() == 0) {
            return strusbName;
        }

        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        if (deviceIterator.hasNext()) {
            UsbDevice device = (UsbDevice) deviceIterator.next();
            strusbName = device.getDeviceName();

            Log.d("", "Name: " + device.getDeviceName() + "\n"
                    + "VID: " + device.getVendorId()
                    + "       PID: " + device.getProductId());

            UsbInterface intf = device.getInterface(0);
            int epc = 0;
            epc = intf.getEndpointCount();
            Log.d("", "Endpoints:" + epc + "\n");

            Log.d("", "Permission:" + Boolean.toString(mUsbManager.hasPermission(device)) + "\n");

            UsbDeviceConnection connection = mUsbManager.openDevice(device);
            if (null == connection) {
                Log.d("", "(unable to establish connection)\n");
            } else {

                // Claims exclusive access to a UsbInterface.
                // This must be done before sending or receiving data on
                // any UsbEndpoints belonging to the interface.
                connection.claimInterface(intf, true);

                // getRawDescriptors can be used to access descriptors
                // not supported directly via the higher level APIs,
                // like getting the manufacturer and product names.
                // because it returns bytes, you can get a variety of
                // different data types.
                byte[] rawDescs = connection.getRawDescriptors();
                String manufacturer = "", product = "";

                try {
                    byte[] buffer = new byte[255];
                    int idxMan = rawDescs[14];
                    int idxPrd = rawDescs[15];

                    int rdo = connection.controlTransfer(UsbConstants.USB_DIR_IN
                                    | UsbConstants.USB_TYPE_STANDARD, STD_USB_REQUEST_GET_DESCRIPTOR,
                            (LIBUSB_DT_STRING << 8) | idxMan, 0, buffer, 0xFF, 0);
                    manufacturer = new String(buffer, 2, rdo - 2, "UTF-16LE");

                    rdo = connection.controlTransfer(UsbConstants.USB_DIR_IN
                                    | UsbConstants.USB_TYPE_STANDARD, STD_USB_REQUEST_GET_DESCRIPTOR,
                            (LIBUSB_DT_STRING << 8) | idxPrd, 0, buffer, 0xFF, 0);
                    product = new String(buffer, 2, rdo - 2, "UTF-16LE");

/*                int rdo = connection.controlTransfer(UsbConstants.USB_DIR_IN
                        | UsbConstants.USB_TYPE_STANDARD, STD_USB_REQUEST_GET_DESCRIPTOR,
                        (LIBUSB_DT_STRING << 8) | idxMan, 0x0409, buffer, 0xFF, 0);*/

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.d("", "Manufacturer:" + manufacturer + "\n");
                Log.d("", "Product:" + product + "\n");
                Log.d("", "Serial#:" + connection.getSerial() + "\n");
            }

        }
        return strusbName;

    }
}
