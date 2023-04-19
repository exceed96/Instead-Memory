import React, { useRef } from "react";
import styles from "./WriteMemo.module.css";

const WriteMemo = (props) => {
  const sendtitle = useRef("");
  const sendmainText = useRef("");

  const postUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo";

  const sendData = JSON.stringify({
    title: sendtitle.current.value,
    content: sendmainText.current.value,
  });

  const memoSubmitHandler = async (event) => {
    event.preventDefault();
    console.log(sendData);
    await fetch(postUrl, {
      method: "post",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("userToken")}`,
        "Content-Type": "application/json",
      },
      mode: "cors",
      body: sendData,
    });
    // const data = await response.json();
    // console.log(data);
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
            ref={sendtitle}
          />
          <hr />
          <textarea
            name="content"
            id="memo-text"
            className={styles["memo-text"]}
            cols="38"
            rows="3"
            placeholder="본문"
            ref={sendmainText}
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
