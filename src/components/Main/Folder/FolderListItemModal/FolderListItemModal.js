import { useEffect } from "react";
import ReactDOM from "react-dom";
import { useDispatch, useSelector } from "react-redux";
import styles from "./FolderListItemModal.module.css";
import axios from "axios";
import getAccessToken from "../../../../store/accessTokenState";
import { folderModalActions } from "../../../../store/folderModalState";

const Overlay = () => {
  return <section className={styles.modalOverlay}></section>;
};

const FolderListItemModalContent = (props) => {
  const getFolderUuid = useSelector(state => state.folderModalState.selectFolder);
  const selectFolder = useSelector(state => state.folderModalState.selectFolderName);
  const dispatch = useDispatch();


  const putFolders = async () => {
    console.log(getFolderUuid);
    if (!getFolderUuid){
      dispatch(folderModalActions.setSelectFolderName("no choose Dir"));
      return ;
    }
    else{
      const folderPutUrl = "https://api.insteadmemo.kr/v2/dir/memo/dirfk";
      const header = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
      };
      const finalData = JSON.stringify({
        uuid: getFolderUuid,
        memoUuid: props.memoUuid,
      });
      const data = await axios({
        method: "put",
        url: folderPutUrl,
        headers: header,
        data: finalData,
        withCredentials: true,
      });
      if (data.status === 200){
        dispatch(folderModalActions.setFolderErrorCode(200));
        props.setFolderModal(prevState => !prevState);
      }
    }
  };

  const putFoldersHandler = async () => {
    try {
      await putFolders();
    } catch (error) {
      console.log(error);
      dispatch(getAccessToken(error, putFolders));
    }
  };

  useEffect(() => {
    dispatch(folderModalActions.setSelectFolderName(""));
    dispatch(folderModalActions.setSelectFolderUuid(""));
  }, [dispatch]);

  return (
    <section className={styles.folderModalContainer}>
      <header className={styles.headerContainer}>
        <section className={styles.headerContainer}>
          <section className={styles.headerLeft}>
            <i
              class="fa-solid fa-chevron-left"
              onClick={props.exchangeModal}
            ></i>
            <strong className={styles.title}>디렉토리</strong>
          </section>
          <section className={styles.headerRight}>
            <strong>{selectFolder}</strong>
          </section>
        </section>
      </header>
      <main className={styles.mainContainer}>{props.getFoldersDatas}</main>
      <footer className={styles.footerContainer}>
        <button onClick={putFoldersHandler}>선택완료</button>
      </footer>
    </section>
  );
};

const FolderListItemModal = (props) => {
  return (
    <>
      {ReactDOM.createPortal(
        <Overlay closeModal={props.closeModal} />,
        document.getElementById("overlay")
      )}
      {ReactDOM.createPortal(
        <FolderListItemModalContent
          exchangeModal={props.exchangeModal}
          getFoldersDatas={props.getFoldersDatas}
          memoUuid={props.memoUuid}
          setFolderModal={props.setFolderModal}
        />,
        document.getElementById("modalContent")
      )}
    </>
  );
};

export default FolderListItemModal;
