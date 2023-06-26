import styles from "./FolderListItem.module.css";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import getAccessToken from "../../../../store/accessTokenState";
import { folderModalActions } from "../../../../store/folderModalState";

const FolderListItem = (props) => {
  const dispatch = useDispatch();
  const folderDeleteUrl = "https://api.insteadmemo.kr/v2/dir/delete";

  const folderInfo = useSelector((state) => state.folderModalState.folderInfo);
  const changeEditSection = () => {
    props.changeEditSection();
    dispatch(
      folderModalActions.setSeletcFolder({
        dirName: props.dirName,
        uuid: props.uuid,
      })
    );
  };

  const deleteFolder = async (getUuid) => {
    const finalData = JSON.stringify({
      uuid: getUuid,
    });
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const data = await axios({
      method: "delete",
      url: folderDeleteUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    props.setGetFoldersNameFlag(true);
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
    <li className={styles.registeredFolderListItem}>
      {props.dirName}
      <section className={styles.registeredFolderListItemIcon}>
        <i class="fa-solid fa-pen-to-square" onClick={changeEditSection}></i>
        <i
          class="fa-solid fa-trash"
          onClick={() => deleteFolderHandler(props.uuid)}
        ></i>
      </section>
    </li>
  );
};

export default FolderListItem;
