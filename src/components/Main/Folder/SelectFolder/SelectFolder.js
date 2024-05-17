import styles from "./SelectFolder.module.css";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { folderModalActions } from "../../../../store/folderModalState";
import getAccessToken from "../../../../store/accessTokenState";
import removeFolder from "../../../../assets/removeFolder.png";
import editFolderName from "../../../../assets/editFolder.png";

const SelectFolder = (props) => {
  const dispatch = useDispatch();
  const closeFolder = () => {
    dispatch(folderModalActions.setSelectFolderUuid(null));
  };

  const selectFolderList = useSelector(
    (state) => state.folderModalState.selectFolderList
  );

  const deleteFolder = async (getUuid) => {
    const folderDeleteUrl = "https://api.insteadmemo.kr/v2/dir/delete";
    const finalData = JSON.stringify({
      uuid: props.uuid,
    });
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    await axios({
      method: "delete",
      url: folderDeleteUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    dispatch(folderModalActions.setGetFolderFlag());
    closeFolder();
  };

  const deleteFolderHandler = async (getUuid) => {
    try {
      await deleteFolder(getUuid);
    } catch (error) {
      console.log(error);
      dispatch(getAccessToken(error));
      await deleteFolder(getUuid);
    }
  };

  return (
    <section className={styles.selectFolderContainer}>
      <header className={styles.headerContainer}>
        <i class="fa-solid fa-chevron-left" onClick={closeFolder}></i>
        <strong>{props.dirName}</strong>
        <section className={styles.folderIcon}>
          <img src={editFolderName} alt="editFolderName" />
          <img
            src={removeFolder}
            onClick={deleteFolderHandler}
            alt="deleteFolder"
          />
        </section>
      </header>
      <main>{selectFolderList}</main>
    </section>
  );
};

export default SelectFolder;
