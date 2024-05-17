import React,{ useEffect, useCallback } from "react";
import WriteMemo from "./WriteMemoComponent/WriteMemo";
import styles from "./Main.module.css";
import MemoSelectSection from "./MemoSelectSection/MemeoSelectSection";
import SavedMemo from "./SavedMemo/SavedMemo";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../../store/mainState";
import { folderModalActions } from "../../store/folderModalState";
import getAccessToken from "../../store/accessTokenState";
import Folder from "./Folder/Folder";
import FolderListItem from "./Folder/FolderListItem/FolderListItem";
import { deleteMemoActions } from "../../store/deleteMemoState";
import TrashComponent from "./TrashComponent/TrashComponent";
import FolderSuccessModal from "./Folder/FolderSuccessModal/FolderSuccessModal";

const Main = () => {
  const getUrl = "https://api.insteadmemo.kr/v1/memo/find";
  const getData = useSelector((state) => state.mainState.getData);
  const receiveData = useSelector((state) => state.mainState.receiveData);
  const receiveImportData = useSelector(
    (state) => state.mainState.receiveImportData
  );

  const currentState = useSelector(
    (state) => state.selectSectionState.currentSection
  );

  const isOpen = useSelector((state) => state.expansionMemoState.isOpen);

  const getFoldersNameFlag = useSelector(
    (state) => state.folderModalState.getFolderFlag
  );

  const folderErrorCode = useSelector(
    (state) => state.folderModalState.folderErrorCode
  );

  const dispatch = useDispatch();

  const getFolders = useCallback(async () => {
    const folderGetUrl = "https://api.insteadmemo.kr/v2/dir/find";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    if (getFoldersNameFlag) {
      const data = await axios({
        method: "get",
        url: folderGetUrl,
        headers: header,
        withCredentials: true,
      });
      const filterGetFolderName = data.data.data.map((el) => (
        <FolderListItem key={el.uuid} uuid={el.uuid} dirName={el.dirName} />
      ));
      dispatch(folderModalActions.setFolderList(filterGetFolderName));
      dispatch(folderModalActions.setGetFolderFlag());
    }
  }, [dispatch, getFoldersNameFlag]);

  const getFoldersHandler = useCallback(async () => {
    try {
      await getFolders();
    } catch (error) {
      dispatch(getAccessToken(error, getFolders));
    }
  },[dispatch, getFolders]);

  const getMemos = useCallback(async () => {
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    if (getData) {
      const data = await axios({
        method: "get",
        url: getUrl,
        headers: header,
        withCredentials: true,
      });
      const sendDataFilter = data.data.data.filter((el) => {
        if (!el.trash) {
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

      const importDataFilter = data.data.data.filter((el) => {
        if (el.important && !el.trash) {
          return true;
        }
        return false;
      });

      const importData = importDataFilter.map((memo) => (
        <SavedMemo
          title={memo.title}
          content={memo.content}
          important={memo.important}
          uuid={memo.uuid}
          key={memo.uuid}
          trash={memo.trash}
          createdDate={memo.createdDate}
        />
      ));

      const deleteMemoDataFilter = data.data.data.filter((el) => {
        if (el.trash) {
          return true;
        }
        return false;
      });

      const deleteMemoData = deleteMemoDataFilter.map((memo) => (
        <SavedMemo
          title={memo.title}
          content={memo.content}
          important={memo.important}
          uuid={memo.uuid}
          key={memo.uuid}
          trash={memo.trash}
          createdDate={memo.createdDate}
          checkbox={true}
        />
      ));
      dispatch(deleteMemoActions.receiveDeleteData(deleteMemoData));
      dispatch(mainActions.receiveImportData(importData));
      dispatch(mainActions.receiveData(sendData));
      dispatch(mainActions.setGetData());
    }
  }, [dispatch, getData]);

  const getMemosHandler = useCallback(async () => {
    try {
      await getMemos();
    } catch (error) {
      dispatch(getAccessToken(error, getMemos));
    }
  }, [dispatch, getMemos]);

  if (isOpen) {
    document.body.style.overflowY = "hidden";
  } else {
    document.body.style.overflowY = "auto";
  }

  useEffect(() => {
    getMemosHandler();
    getFoldersHandler();
  }, [getData, getFoldersNameFlag, getFoldersHandler, getMemosHandler]);

  return (
    <>
      {folderErrorCode === 200 && <FolderSuccessModal/>}
      <main className={styles.main}>
        <WriteMemo
          entireMemoCount={receiveData.length}
          importMemoCount={receiveImportData.length}
        />
        <MemoSelectSection />
        <section className={styles.savedMemo}>
          {currentState.note && (
            <ul className={styles.savedMemoContainer}>{receiveData}</ul>
          )}
          {currentState.important && (
            <ul className={styles.savedMemoContainer}>{receiveImportData}</ul>
          )}
          {currentState.folder && <Folder />}

          {currentState.delete && <TrashComponent />}
        </section>
      </main>
    </>
  );
};

export default Main;
