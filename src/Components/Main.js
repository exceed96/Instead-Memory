import React, { useEffect, useCallback } from "react";
import styles from "./Main.module.css";
import SavedMemo from "./SavedMemo/SavedMemo";
import WriteMemo from "./WriteMemo/WriteMemo";
import ImportMemoToggle from "./ImportMemo/ImportMemoToggle";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../store/mainState";

const Main = () => {
  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";
  const header = {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };
  const getData = useSelector((state) => state.mainState.getData);
  const receiveData = useSelector((state) => state.mainState.receiveData);
  const dispatch = useDispatch();

  const axiosDataHandler = useCallback(async () => {
    if (getData) {
      const data = await axios({
        method: "get",
        url: getUrl,
        headers: header,
      });
      const sendData = data.data.data.map((el) => (
        <SavedMemo
          title={el.title}
          content={el.content}
          uuid={el.uuid}
          important={el.important}
        />
      ));
      dispatch(mainActions.receiveData(sendData));
      dispatch(mainActions.setGetData());
    }
  }, [getData]);

  useEffect(() => {
    axiosDataHandler();
  }, [axiosDataHandler]);

  return (
    <main>
      <WriteMemo />
      {/* <!-- 즐겨찾기 토글 --> */}
      <section>
        <ImportMemoToggle />
      </section>
      {/* <!-- 저장된 메모 영역 --> */}
      <div className={styles["main-savedMemo"]}>
        <ul className={styles["savedMemo-container"]}>
          {/* <!-- 예시 메모  --> */}
          {receiveData}
        </ul>
      </div>
    </main>
  );
};

export default Main;
