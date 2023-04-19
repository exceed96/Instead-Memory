import React from "react";
import styles from "./SavedMemo.module.css";

const SavedMemo = () => {
  return (
    <li className={styles.memo}>
      <form className={styles.savedMemo}>
        <div className={styles.savedMemoTop}>
          <div className={styles.savedMemoLeft}>
            <input
              type="text"
              id="memo-title"
              className={styles["memo-title"]}
              placeholder="제목"
              size="30"
            />
            <hr />
            <textarea
              name=""
              id="memo-text"
              className={styles["memo-text"]}
              cols="30"
              rows="3"
              placeholder="본문"
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
            <button className={styles["main-savedMemoSave"]}>저장</button>
          </div>
        </div>
      </form>
    </li>
  );
};

export default SavedMemo;
