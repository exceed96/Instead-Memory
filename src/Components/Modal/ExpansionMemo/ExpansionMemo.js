import React, { useEffect, useState } from "react";
import styles from "./ExpansionMemo.module.css";
import axios from "axios";

const ExpansionMemo = (props) => {
  const [singleMemo, setSingleMemo] = useState({});
  const deleteUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/delete";
  const deleteMemo = async (evt) => {
    evt.preventDefault();
    //const sendId = JSON.stringify(idxObj);
    //console.log(sendId);
    await axios.delete(`${deleteUrl}/${props.uuid}`);
    props.setGetData(true);
  };

  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find/userInfo";
  const header = {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  const sendData = {
    uuid: props.uuid,
  };

  useEffect(() => {
    const getMemo = async (evt) => {
      const finalSendData = JSON.stringify(sendData);
      console.log(finalSendData);
      const data = await axios({
        method: "get",
        url: getUrl,
        params: sendData,
        headers: header,
      });
      setSingleMemo(data.data.data[0]);
    };
    getMemo();
  }, [sendData]);
  return (
    <>
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
                value={singleMemo.title}
              />
              <hr />
              <textarea
                name=""
                id="memo-text"
                className={styles["memo-text"]}
                cols="30"
                placeholder="본문"
                value={singleMemo.content}
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
      <section
        onClick={props.clickMemo}
        className={styles.modalOverlay}
      ></section>
    </>
  );
};

export default ExpansionMemo;
