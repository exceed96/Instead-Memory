import React from "react";
import styles from "./SavedMemo.module.css";
import axios from "axios";

const SavedMemo = (props) => {
  const deleteUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/delete";
  const deleteMemo = async (evt) => {
    evt.preventDefault();
    //const sendId = JSON.stringify(idxObj);
    //console.log(sendId);
    await axios.delete(`${deleteUrl}/${props.uuid}`);
    props.setGetData(true);
  };
  return (
    <li className={styles.memo}>
      <form className={styles.savedMemo} onSubmit={deleteMemo}>
        <div className={styles.savedMemoTop}>
          <div className={styles.savedMemoLeft}>
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
            <i className="fa-regular fa-star"></i>
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
  );
};

export default SavedMemo;
