import React, { useEffect, useState } from "react";
import styles from "./ExpansionMemo.module.css";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../../../store/mainState";
import { expansionMemoActions } from "../../../store/expansionMemoState";
import { getImportMemo } from "../../../store/importMemoState";

const ExpansionMemo = (props) => {
  const [expansionMemo, setExpansionMemo] = useState({});
  const updateTitleText = useSelector(
    (state) => state.expansionMemoState.updateTitleText
  );
  const updateMainText = useSelector(
    (state) => state.expansionMemoState.updateMainText
  );

  const dispatch = useDispatch();

  const header = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };
  const putUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/update";

  const updateTitleHandler = (evt) => {
    dispatch(expansionMemoActions.updateTitleTextHandler(evt.target.value));
  };

  const updateContentHandler = (evt) => {
    dispatch(expansionMemoActions.updateMainTextHandler(evt.target.value));
  };

  const updateData = {
    title: updateTitleText === "" ? expansionMemo.title : updateTitleText,
    content: updateMainText === "" ? expansionMemo.content : updateMainText,
    important: expansionMemo.important,
    uuid: expansionMemo.uuid,
  };

  const updateMemo = async (evt) => {
    evt.preventDefault();
    const isUpdate = window.confirm("Update???");
    const finalData = JSON.stringify(updateData);
    if (isUpdate) {
      const response = await axios({
        url: putUrl,
        method: "put",
        headers: header,
        data: finalData,
      });
      if (response.status >= 200 && response.status < 300) {
        dispatch(mainActions.setGetData());
        dispatch(getImportMemo());
        props.clickMemo();
      }
    }
  };

  const sendData = {
    uuid: props.uuid,
  };

  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find/userInfo";

  useEffect(() => {
    const getMemo = async (evt) => {
      const data = await axios({
        method: "get",
        url: getUrl,
        params: sendData,
        headers: header,
      });
      setExpansionMemo(data.data.data[0]);
    };
    getMemo();
    return () => {
      getMemo();
    };
  }, []);

  return (
    <>
      <li className={styles.memo}>
        <form className={styles.savedMemo} onSubmit={updateMemo}>
          <div className={styles.savedMemoTop}>
            <div className={styles.savedMemoLeft}>
              <input
                type="text"
                id="memo-title"
                className={styles["memo-title"]}
                placeholder="제목"
                size="30"
                defaultValue={expansionMemo.title}
                onChange={updateTitleHandler}
                required
              />
              <hr />
              <textarea
                name=""
                id="memo-text"
                className={styles["memo-text"]}
                cols="30"
                placeholder="본문"
                defaultValue={expansionMemo.content}
                onChange={updateContentHandler}
                required
              ></textarea>
            </div>
            <div className={styles.savedMemoRight}>
              {expansionMemo.important ? (
                <i class="fa-solid fa-star"></i>
              ) : (
                <i className="fa-regular fa-star" i></i>
              )}
              <i className="fa-regular fa-folder"></i>
            </div>
          </div>
          <div className={styles.savedMemoBottom}>
            <div className={styles.savedMemoBottomLeft}></div>
            <div className={styles.savedMemoBottomRight}>
              {/* <button className={styles["main-savedMemoSave"]}>삭제</button> */}
              <button className={styles["main-savedMemoSave"]}>저장</button>
            </div>
          </div>
        </form>
      </li>
      <section
        onClick={props.clickMemo}
        className={styles.modalOverlay}
      ></section>
    </>
  );
};

export default ExpansionMemo;
