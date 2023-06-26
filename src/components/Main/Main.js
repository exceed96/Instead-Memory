import { useEffect, useState } from "react";
import WriteMemo from "./WriteMemoComponent/WriteMemo";
import styles from "./Main.module.css";
import MemoSelectSection from "./MemoSelectSection/MemeoSelectSection";
import SavedMemo from "./SavedMemo/SavedMemo";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../../store/mainState";
import getAccessToken from "../../store/accessTokenState";
import Folder from "./Folder/Folder";

const Main = () => {
  const [a, setA] = useState("apple");

  const getUrl = "https://api.insteadmemo.kr/v1/memo/find";
  const getData = useSelector((state) => state.mainState.getData);
  const receiveData = useSelector((state) => state.mainState.receiveData);
  const receiveImportData = useSelector(
    (state) => state.mainState.receiveImportData
  );
  const currentState = useSelector(
    (state) => state.selectSectionState.currentSection
  );
  const importantState = useSelector(
    (state) => state.selectSectionState.currentSection.important
  );
  const isOpen = useSelector((state) => state.expansionMemoState.isOpen);

  const dispatch = useDispatch();

  const getMemos = async () => {
    const header = {
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    if (getData) {
      const data = await axios({
        method: "get",
        url: getUrl,
        headers: header,
        withCredentials: true,
      });
      const sendData = data.data.data.map((el) => (
        <SavedMemo
          title={el.title}
          content={el.content}
          uuid={el.uuid}
          important={el.important}
          key={el.uuid}
        />
      ));
      const importDataFilter = data.data.data.filter((el) => {
        if (el.important) {
          return el;
        }
      });
      const importData = importDataFilter.map((memo) => (
        <SavedMemo
          title={memo.title}
          content={memo.content}
          important={memo.important}
          uuid={memo.uuid}
          key={memo.uuid}
        />
      ));
      dispatch(mainActions.receiveImportData(importData));
      dispatch(mainActions.receiveData(sendData));
      dispatch(mainActions.setGetData());
    }
  };

  const getMemosHandler = async () => {
    try {
      await getMemos();
    } catch (error) {
      dispatch(getAccessToken(error, getMemos));
    }
  };

  if (isOpen) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "auto";
  }

  console.log(isOpen);
  useEffect(() => {
    getMemosHandler();
  }, [getData]);

  return (
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
      </section>
    </main>
  );
};

export default Main;
