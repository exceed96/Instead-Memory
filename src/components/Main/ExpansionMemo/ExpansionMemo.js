import { useState, useEffect } from "react";
import ReactDom, { createPortal } from "react-dom";
import { useDispatch, useSelector } from "react-redux";
import styles from "./ExpansionMemo.module.css";
import React from "react";
import axios from "axios";
import { mainActions } from "../../../store/mainState";
import getAccessToken from "../../../store/accessTokenState";
import { expansionMemoActions } from "../../../store/expansionMemoState";

const Overlay = (props) => {
  const dispatch = useDispatch();
  const closeModal = () => {
    dispatch(expansionMemoActions.setModalHandler());
    props.closeModal();
  };
  return (
    <section className={styles.modalOverlay} onClick={closeModal}></section>
  );
};

const ExpansionMemoContent = (props) => {
  const [expansionMemo, setExpansionMemo] = useState({});
  const deleteUrl = "https://api.insteadmemo.kr/v1/memo/delete";
  const putUrl = "https://api.insteadmemo.kr/v1/memo/update";

  const dispatch = useDispatch();

  const updateTitleText = useSelector(
    (state) => state.expansionMemoState.updateTitleText
  );
  const updateMainText = useSelector(
    (state) => state.expansionMemoState.updateMainText
  );

  const updateImportant = useSelector(
    (state) => state.expansionMemoState.updateImportant
  );

  const updateTitleHandler = (evt) => {
    dispatch(expansionMemoActions.updateTitleTextHandler(evt.target.value));
  };

  const updateContentHandler = (evt) => {
    dispatch(expansionMemoActions.updateMainTextHandler(evt.target.value));
  };

  const updateImportantHandler = () => {
    dispatch(expansionMemoActions.updateImportantHandler());
  };

  const deleteMemo = async (evt) => {
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const finalData = JSON.stringify({ uuid: props.uuid });
    const response = await axios({
      method: "delete",
      url: deleteUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    dispatch(mainActions.setGetData());
    // dispatch(getImportMemo());
    props.closeModal();
  };

  const deleteMemoHandler = async (evt) => {
    try {
      evt.preventDefault();
      await deleteMemo();
    } catch (error) {
      dispatch(getAccessToken(error, deleteMemo));
    }
  };

  const updateData = {
    title: updateTitleText,
    content: updateMainText,
    important: updateImportant,
    uuid: expansionMemo.uuid,
  };
  const updateMemo = async (evt) => {
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };

    const finalData = JSON.stringify(updateData);

    const response = await axios({
      url: putUrl,
      method: "put",
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    if (response.status >= 200 && response.status < 300) {
      dispatch(mainActions.setGetData());
      props.closeModal();
    }
  };

  const updateMemoHandler = async (evt) => {
    const isUpdate = window.confirm("Update???");
    try {
      if (isUpdate) {
        evt.preventDefault();
        await updateMemo();
      }
    } catch (error) {
      dispatch(getAccessToken(error, updateMemo));
    }
  };

  const getMemo = async () => {
    const getUrl = "https://api.insteadmemo.kr/v1/memo/find/userInfo";
    const sendData = {
      uuid: props.uuid,
    };
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const response = await axios({
      method: "get",
      url: getUrl,
      params: sendData,
      withCredentials: true,
      headers: header,
    });
    setExpansionMemo(response.data.data[0]);
    dispatch(
      expansionMemoActions.setImportantHandler(response.data.data[0].important)
    );
    dispatch(
      expansionMemoActions.updateTitleTextHandler(response.data.data[0].title)
    );
    dispatch(
      expansionMemoActions.updateMainTextHandler(response.data.data[0].content)
    );
  };

  const getMemoHandler = async () => {
    try {
      await getMemo();
    } catch (error) {
      dispatch(getAccessToken(error, getMemo));
    }
  };

  useEffect(() => {
    getMemoHandler();
  }, []);

  return (
    <section className={styles.memo}>
      <form className={styles.writeMemo}>
        <div className={styles.writeMemoTop}>
          <input
            type="text"
            id="memo-title"
            className={styles["memo-title"]}
            // placeholder=""
            size="30"
            required
            maxLength="10"
            onChange={updateTitleHandler}
            defaultValue={expansionMemo.title}
          />
          {!updateImportant && (
            <i
              className="fa-regular fa-star"
              onClick={updateImportantHandler}
            ></i>
          )}
          {updateImportant && (
            <i class="fa-solid fa-star" onClick={updateImportantHandler}></i>
          )}
        </div>
        <div className={styles.writeMemoMiddle}>
          <textarea
            name=""
            id="memo-text"
            className={styles["memo-text"]}
            maxLength="500"
            cols="30"
            // placeholder="본문"
            required
            onChange={updateContentHandler}
            defaultValue={expansionMemo.content}
          ></textarea>
        </div>
        <div className={styles.writeMemoBottom}>
          <i class="fa-regular fa-trash-can" onClick={deleteMemoHandler}></i>
          <i class="fa-regular fa-floppy-disk" onClick={updateMemoHandler}></i>
          <i className="fa-regular fa-folder"></i>
        </div>
      </form>
    </section>
  );
};

const ExpansionMemo = (props) => {
  return (
    <>
      {ReactDom.createPortal(
        <Overlay closeModal={props.closeModal} />,
        document.getElementById("overlay")
      )}
      {ReactDom.createPortal(
        <ExpansionMemoContent
          closeModal={props.closeModal}
          uuid={props.uuid}
          important={props.important}
        />,
        document.getElementById("modalContent")
      )}
    </>
  );
};

export default ExpansionMemo;
