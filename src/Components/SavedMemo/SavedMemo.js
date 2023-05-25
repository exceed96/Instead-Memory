import React, { useState } from "react";
import styles from "./SavedMemo.module.css";
import axios from "axios";
import ExpansionMemo from "../Modal/ExpansionMemo/ExpansionMemo";

const SavedMemo = (props) => {
  const [expansionMemo, setExpansionMemo] = useState(false);
  const [importMemo, setImportMemo] = useState(false);

  const deleteUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/delete";
  const putUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/important";
  const deleteMemo = async (evt) => {
    evt.preventDefault();
    //const sendId = JSON.stringify(idxObj);
    //console.log(sendId);
    await axios.delete(`${deleteUrl}/${props.uuid}`);
    props.setGetData(true);
  };

  const header = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  const sendData = { uuid: props.uuid };

  const changeImportMemo = async () => {
    setImportMemo((prevState) => !prevState);
    const finalData = JSON.stringify(sendData);
    console.log(finalData);
    await axios({
      method: "put",
      url: putUrl,
      headers: header,
      data: finalData,
    });
  };

  const clickMemo = () => {
    setExpansionMemo((prevState) => {
      return !prevState;
    });
  };

  return (
    <>
      <li className={styles.memo}>
        <form className={styles.savedMemo} onSubmit={deleteMemo}>
          <div className={styles.savedMemoTop}>
            <div onClick={clickMemo} className={styles.savedMemoLeft}>
              <input
                type="text"
                id="memo-title"
                className={styles["memo-title"]}
                placeholder="제목"
                size="30"
                value={props.title}
              />
              <hr />
              <textarea
                name=""
                id="memo-text"
                className={styles["memo-text"]}
                cols="30"
                rows="3"
                placeholder="본문"
                value={props.content}
              ></textarea>
            </div>
            <div className={styles.savedMemoRight}>
              {!props.important && (
                <i
                  className="fa-regular fa-star"
                  i
                  onClick={changeImportMemo}
                ></i>
              )}
              {props.important && (
                <i class="fa-solid fa-star" onClick={changeImportMemo}></i>
              )}
              <i className="fa-regular fa-folder"></i>
            </div>
          </div>
          <div className={styles.savedMemoBottom}>
            <div className={styles.savedMemoBottomLeft}></div>
            <div className={styles.savedMemoBottomRight}>
              <button className={styles["main-savedMemoSave"]}>삭제</button>
              <button className={styles["main-savedMemoSave"]}>저장</button>
            </div>
          </div>
        </form>
      </li>
      {expansionMemo && (
        <ExpansionMemo clickMemo={clickMemo} uuid={props.uuid} />
      )}
    </>
  );
};

export default SavedMemo;
