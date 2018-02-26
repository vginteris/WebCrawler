package norwegian.service;

import norwegian.models.OsloRigaData;

import java.util.List;

/**
 * Created by vgint on 2/24/2018.
 */
public interface IFileService {
    void writeDataToFile(List<OsloRigaData> osloRigaDataList);
}
