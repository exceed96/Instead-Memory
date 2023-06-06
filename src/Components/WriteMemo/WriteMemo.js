import React from "react";
import styles from "./WriteMemo.module.css";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../../store/mainState";
import { writeMemoActions } from "../../store/writeMemoState";

const WriteMemo = (props) => {
  const dispatch = useDispatch();

  const inputTitleText = useSelector(
    (state) => state.writeMemoState.inputTitleText
  );
  const inputMainText = useSelector(
    (state) => state.writeMemoState.inputMainText
  );
  const importMemo = useSelector((state) => state.writeMemoState.importMemo);

  const changeTitleHandler = (evt) => {
    dispatch(writeMemoActions.changeTitleText(evt.target.value));
  };

  const changeMainTextHandler = (evt) => {
    dispatch(writeMemoActions.changeMainText(evt.target.value));
  };

  const changeImportMemo = () => {
    dispatch(writeMemoActions.changeImportMemo());
  };

  const postUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo";

  const header = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  const memoSubmitHandler = async (evt) => {
    evt.preventDefault();
    const finalData = JSON.stringify({
      title: inputTitleText,
      content: inputMainText,
      important: importMemo,
    });
    const response = await axios({
      method: "post",
      url: postUrl,
      headers: header,
      data: finalData,
    });
    if (importMemo) {
      dispatch(writeMemoActions.changeImportMemo());
    }
    dispatch(writeMemoActions.changeTitleText(""));
    dispatch(writeMemoActions.changeMainText(""));
    dispatch(mainActions.setGetData());
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
            value={inputTitleText}
            required
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
            value={inputMainText}
            required
          ></textarea>
        </div>
        {/* <!-- 메모 우측 영역 --> */}
        <div className={styles["main-memoRight"]}>
          {/* <!-- 즐겨찾기 div박스 삭제(*) --> */}
          {!importMemo && (
            <i className="fa-regular fa-star" i onClick={changeImportMemo}></i>
          )}
          {importMemo && (
            <i class="fa-solid fa-star" onClick={changeImportMemo}></i>
          )}
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
        </div>
      </div>
    </form>
  );
};

export default WriteMemo;
