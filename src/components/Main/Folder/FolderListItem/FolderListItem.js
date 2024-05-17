import { useState, useEffect } from "react";
import styles from "./FolderListItem.module.css";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import getAccessToken from "../../../../store/accessTokenState";
import { folderModalActions } from "../../../../store/folderModalState";
import SavedMemo from "../../SavedMemo/SavedMemo";

const FolderListItem = (props) => {
  const [listCss, setListCss] = useState(
    props.modal
      ? `${styles.registeredFolderListItemModal}`
      : `${styles.registeredFolderListItem}`
  );

  const [selectedDir, setSelectedDir] = useState(false);

  const targetDir = useSelector(
    (state) => state.folderModalState.selectFolderUuid
  );

  const dispatch = useDispatch();

  const FolderEventHandler = () => {
    if (props.modal) {
      if (selectedDir) {
        setListCss(styles.registeredFolderListItemModal);
        dispatch(folderModalActions.setSelectFolderUuid(""));
        dispatch(folderModalActions.setSelectFolderName(""));
        setSelectedDir((prevState) => !prevState);
      } else {
        setListCss(styles.registeredFolderListItemModalChoose);
        dispatch(folderModalActions.setSelectFolderUuid(props.uuid));
        dispatch(folderModalActions.setSelectFolderName(props.dirName));
        setSelectedDir((prevState) => !prevState);
      }
    }
    if (!props.modal) {
      dispatch(folderModalActions.setSelectFolderUuid(props.uuid));
      dispatch(folderModalActions.setSelectFolderName(props.dirName));
      const getMemos = async () => {
        const getUrl = "https://api.insteadmemo.kr/v1/memo/find";
        const header = {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        };
        const data = await axios({
          method: "get",
          url: getUrl,
          headers: header,
          withCredentials: true,
        });
        const sendDataFilter = data.data.data.filter((el) => {
          if (el.directory_id === props.uuid && !el.trash) {
            return true;
          }
          return false;
        });

        const sendData = sendDataFilter.map((el) => (
          <SavedMemo
            title={el.title}
            content={el.content}
            uuid={el.uuid}
            important={el.important}
            key={el.uuid}
            trash={el.trash}
            createdDate={el.createdDate}
          />
        ));
        dispatch(folderModalActions.setSelectFolderList(sendData));
      };

      const getMemosHandler = async () => {
        try {
          await getMemos();
        } catch (error) {
          dispatch(getAccessToken(error, getMemos));
        }
      };
      getMemosHandler();
    }
  };

  useEffect(() => {
    console.log(1);
    if (targetDir !== props.uuid) {
      setListCss(styles.registeredFolderListItemModal);
    }
  }, [props.uuid, targetDir]);

  return (
    <li className={listCss} onClick={FolderEventHandler}>
      {props.dirName}
      <section className={styles.registeredFolderListItemIcon}></section>
    </li>
  );
};

export default FolderListItem;
