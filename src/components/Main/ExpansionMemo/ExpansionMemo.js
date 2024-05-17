import { useEffect, useCallback } from "react";
import ReactDOM from "react-dom";
import { useDispatch, useSelector } from "react-redux";
import styles from "./ExpansionMemo.module.css";
import React from "react";
import axios from "axios";
import { mainActions } from "../../../store/mainState";
import getAccessToken from "../../../store/accessTokenState";
import { expansionMemoActions } from "../../../store/expansionMemoState";
import solidStar from "../../../assets/solidStar.png";
import FolderListItem from "../Folder/FolderListItem/FolderListItem";

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
  const trashUrl = "https://api.insteadmemo.kr/v1/memo/trash";
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
    const finalData = JSON.stringify({ uuid: props.uuid, trash: true });
    await axios({
      method: "put",
      url: trashUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    dispatch(mainActions.setGetData());
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
    uuid: props.uuid,
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

  const getFolders = useCallback(async () => {
    const folderGetUrl = "https://api.insteadmemo.kr/v2/dir/find";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const data = await axios({
      method: "get",
      url: folderGetUrl,
      headers: header,
      withCredentials: true,
    });
    const filterGetFolderName = data.data.data.map((el) => (
      <FolderListItem
        key={el.uuid}
        uuid={el.uuid}
        dirName={el.dirName}
        modal="modal"
      />
    ));
    props.setGetFoldersDatas(filterGetFolderName);
  }, [props]);

  const getFoldersHandler = useCallback(async () => {
    try {
      await getFolders();
    } catch (error) {
      console.log(error);
      dispatch(getAccessToken(error, getFolders));
    }
  }, [dispatch, getFolders]);

  useEffect(() => {
    getFoldersHandler();
  }, [getFoldersHandler]);

  return (
    <>
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
              defaultValue={props.title}
            />
            {!updateImportant && !props.trash && (
              <i
                className="fa-regular fa-star"
                onClick={updateImportantHandler}
              ></i>
            )}
            {updateImportant && !props.trash && (
              <img
                src={solidStar}
                className={styles.solidStar}
                alt="important memo"
                onClick={updateImportantHandler}
              />
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
              defaultValue={props.content}
            ></textarea>
          </div>
          <div className={styles.writeMemoBottom}>
            {!props.trash && (
              <i
                class="fa-regular fa-trash-can"
                onClick={deleteMemoHandler}
              ></i>
            )}
            {!props.trash && (
              <i
                class="fa-regular fa-floppy-disk"
                onClick={updateMemoHandler}
              ></i>
            )}
            {!props.trash && (
              <i
                className="fa-regular fa-folder"
                onClick={props.exchangeModal}
              ></i>
            )}
          </div>
        </form>
      </section>
    </>
  );
};

const ExpansionMemo = (props) => {
  return (
    <>
      {ReactDOM.createPortal(
        <Overlay closeModal={props.closeModal} />,
        document.getElementById("overlay")
      )}
      {ReactDOM.createPortal(
        <ExpansionMemoContent
          closeModal={props.closeModal}
          uuid={props.uuid}
          important={props.important}
          title={props.title}
          content={props.content}
          trash={props.trash}
          exchangeModal={props.exchangeModal}
          setGetFoldersDatas={props.setGetFoldersDatas}
        />,
        document.getElementById("modalContent")
      )}
    </>
  );
};

export default ExpansionMemo;
