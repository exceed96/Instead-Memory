import React, { useEffect } from "react";
import styles from "./WriteMemoModal.module.css";
import ReactDom from "react-dom";
import axios from "axios";
import { useSelector, useDispatch } from "react-redux";
import { mainActions } from "../../../../store/mainState";
import { writeMemoActions } from "../../../../store/writeMemoState";
import getAccessToken from "../../../../store/accessTokenState";

const Overlay = (props) => {
  return (
    <section
      className={styles.modalOverlay}
      onClick={props.closeModal}
    ></section>
  );
};

const WriteModalContent = (props) => {
  const postUrl = "https://api.insteadmemo.kr/v1/memo";
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

  const changeMainHandler = (evt) => {
    dispatch(writeMemoActions.changeMainText(evt.target.value));
  };

  const changeImportMemo = () => {
    dispatch(writeMemoActions.changeImportMemo());
  };

  const memoSubmit = async () => {
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const finalData = JSON.stringify({
      title: inputTitleText,
      content: inputMainText,
      important: importMemo,
    });
    await axios({
      method: "post",
      url: postUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    if (importMemo) {
      dispatch(writeMemoActions.changeImportMemo());
    }
    dispatch(writeMemoActions.changeTitleText(""));
    dispatch(writeMemoActions.changeMainText(""));
    dispatch(mainActions.setGetData());
    props.closeModal();
  };

  const memoSubmitHandler = async (evt) => {
    try {
      evt.preventDefault();
      await memoSubmit();
    } catch (error) {
      dispatch(getAccessToken(error, memoSubmit));
    }
  };

  useEffect(() => {
    if (props.important) {
      dispatch(writeMemoActions.setImportMemo(true));
    }
    return () => {
      dispatch(writeMemoActions.setImportMemo(false));
    };
  }, [dispatch, props.important]);

  return (
    <section className={styles.memo}>
      <form className={styles.writeMemo} onSubmit={memoSubmitHandler}>
        <div className={styles.writeMemoTop}>
          <input
            type="text"
            id="memo-title"
            className={styles["memo-title"]}
            placeholder="제목"
            size="30"
            required
            maxLength="10"
            onChange={changeTitleHandler}
            value={props.savedMemo ? props.title : inputTitleText}
          />
          {!importMemo && (
            <i className="fa-regular fa-star" onClick={changeImportMemo}></i>
          )}
          {importMemo && (
            <i class="fa-solid fa-star" onClick={changeImportMemo}></i>
          )}
        </div>
        <div className={styles.writeMemoMiddle}>
          <textarea
            name=""
            id="memo-text"
            className={styles["memo-text"]}
            maxLength="500"
            cols="30"
            placeholder="본문"
            required
            onChange={changeMainHandler}
            value={props.savedMemo ? props.content : inputMainText}
          ></textarea>
        </div>
        <div className={styles.writeMemoBottom}>
          {/* <img className={styles.trash} src={trash} /> */}
          {/* <img className={styles.disk} src={disk} /> */}
          <i class="fa-regular fa-floppy-disk" onClick={memoSubmitHandler}></i>
          <i className="fa-regular fa-folder"></i>
        </div>
      </form>
    </section>
  );
};

const WriteMemoModal = (props) => {
  return (
    <>
      {ReactDom.createPortal(
        <Overlay closeModal={props.closeModal} />,
        document.getElementById("overlay")
      )}
      {ReactDom.createPortal(
        <WriteModalContent
          closeModal={props.closeModal}
          uuid={props.uuid}
          important={props.important}
        />,
        document.getElementById("modalContent")
      )}
    </>
  );
};

export default WriteMemoModal;
