package norwegian.service.impl;

import com.google.gson.Gson;
import norwegian.models.OsloRigaData;
import norwegian.service.IFileService;

import java.io.*;
import java.util.List;

/**
 * Created by vgint on 2/24/2018.
 */
public class FileServiceImp implements IFileService {
    public void writeDataToFile(List<OsloRigaData> osloRigaDataList) {
        Gson gson = new Gson();

        try {
            FileWriter writer = new FileWriter("osloRigaData.json");
            String flightData = gson.toJson(osloRigaDataList);
            writer.write(flightData);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
