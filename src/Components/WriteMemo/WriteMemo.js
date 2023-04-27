import React, { useState, useCallback } from "react";
import styles from "./WriteMemo.module.css";
import axios from "axios";
import SavedMemo from "../SavedMemo/SavedMemo";

const WriteMemo = (props) => {
  // const sendtitle = useRef("");
  // const sendmainText = useRef("");
  const [inputTitle, setInputTitle] = useState("");
  const [mainText, setMainText] = useState("");

  const changeTitleHandler = (evt) => {
    setInputTitle(evt.target.value);
  };

  const changeMainTextHandler = (evt) => {
    setMainText(evt.target.value);
  };

  const postUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo";

  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";
  const sendData = {
    title: inputTitle,
    content: mainText,
  };
  const header = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  const memoSubmitHandler = async (evt) => {
    evt.preventDefault();
    const finalData = JSON.stringify({ title: inputTitle, content: mainText });
    const response = await axios({
      method: "post",
      url: postUrl,
      headers: header,
      data: finalData,
    });
    props.setGetData(true);
    //const data = await axios({
    //  method: "get",
    // url: getUrl,
    // headers : header,
    //})
    //const sendData = data.data.data.map((el) => (<SavedMemo title={el.title} content={el.content} uuid={el.uuid}/>));
    //props.setReceiveData(sendData);
  };

  return (
    <form className={styles["main-memo"]} onSubmit={memoSubmitHandler}>
      {/* <!-- 메모 상단 영역(*) --> */}
      <div className={styles["main-memoTop"]}>
        {/* <!-- 메모 상단 왼쪽--> */}
        <div className={styles["main-memoLeft"]}>
          <input
            type="text"
            id="memo-title"
            className={styles["memo-title"]}
            placeholder="제목"
            name="title"
            size="30"
            onChange={changeTitleHandler}
            // ref={sendtitle}
          />
          <hr />
          <textarea
            name="content"
            id="memo-text"
            className={styles["memo-text"]}
            cols="38"
            rows="3"
            placeholder="본문"
            onChange={changeMainTextHandler}
            // ref={sendmainText}
          ></textarea>
        </div>
        {/* <!-- 메모 우측 영역 --> */}
        <div className={styles["main-memoRight"]}>
          {/* <!-- 즐겨찾기 div박스 삭제(*) --> */}
          <i className="fa-regular fa-star"></i>
          {/* <!-- 폴더 div박스 삭제(*)--> */}
          <i className="fa-regular fa-folder"></i>
        </div>
      </div>

      {/* <!-- 메모 하단 영역 --> */}
      <div className={styles["main-memoBottom"]}>
        {/* <!-- 메모 하단 왼쪽 --> */}
        <div className={styles["main-memoBottomLeft"]}>
          {/* <!-- 기타 기능 --> */}
        </div>
        {/* <!-- 메모 하단 오른쪽 --> */}
        <div className={styles["main-memoBottomRight"]}>
          <button className={styles["main-memoSave"]}>저장</button>
          {/* <!-- 삭제 기능(사이드에 넣을지 하단에 넣을지 고민) --> */}
        </div>
      </div>
    </form>
  );
};

export default WriteMemo;
