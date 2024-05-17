import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import styles from "./SavedMemo.module.css";
import ExpansionMemo from "../ExpansionMemo/ExpansionMemo";
import { expansionMemoActions } from "../../../store/expansionMemoState";
import axios from "axios";
import getAccessToken from "../../../store/accessTokenState";
import { trashSelectDataStateAction } from "../../../store/trashSelectDataState";
import FolderListItemModal from "../Folder/FolderListItemModal/FolderListItemModal";

const SavedMemo = (props) => {
  const [expansionMemo, setExpansionMemo] = useState(false);
  const [expansionMemoData, setExpansionMemoData] = useState({});

  const [getFoldersDatas, setGetFoldersDatas] = useState([]);

  const [isChecked, setIsChecked] = useState(true);
  const dispatch = useDispatch();
  const [folderModal, setFolderModal] = useState(false);

  const selectData = useSelector(
    (state) => state.trashSelectDataState.selectData
  );

  const memoCheckedHandler = (evt) => {
    setIsChecked((prevState) => !prevState);
    if (isChecked) {
      const selectDatas = [...selectData, { uuidDelete: props.uuid }];
      dispatch(trashSelectDataStateAction.selectData(selectDatas));
    } else {
      const selectDatas = selectData.filter(
        (el) => el.uuidDelete !== props.uuid
      );
      dispatch(trashSelectDataStateAction.selectData(selectDatas));
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
    setExpansionMemoData(response.data.data[0]);
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

  const expansionMemoHandler = async () => {
    await getMemoHandler();
    setExpansionMemo((prevState) => !prevState);
    dispatch(expansionMemoActions.setModalHandler());
  };

  const closeExpansionModal = () => {
    setExpansionMemo((prevState) => !prevState);
  };

  const exchangeModal = () => {
    setExpansionMemo((prevState) => !prevState);
    setFolderModal((prevState) => !prevState);
    // dispatch(expansionMemoActions.setModalHandler());
  };

  const date = props.createdDate.slice(0, 10);

  return (
    <>
      {expansionMemo && (
        <ExpansionMemo
          uuid={props.uuid}
          closeModal={closeExpansionModal}
          title={expansionMemoData.title}
          content={expansionMemoData.content}
          trash={props.trash}
          exchangeModal={exchangeModal}
          setGetFoldersDatas={setGetFoldersDatas}
        />
      )}
      {folderModal && (
        <FolderListItemModal
          exchangeModal={exchangeModal}
          memoUuid={props.uuid}
          getFoldersDatas={getFoldersDatas}
          setFolderModal={setFolderModal}
        />
      )}
      <section className={styles.savedMemoContainer}>
        <li className={styles.memo}>
          <section
            className={
              props.checkbox ? styles.savedMemoLeft : styles.savedMemoLeftActive
            }
          >
            <input
              type="checkbox"
              id={props.uuid}
              onChange={memoCheckedHandler}
            ></input>
            <label htmlFor={props.uuid}></label>
          </section>
          <section
            className={styles.savedMemoRight}
            onClick={expansionMemoHandler}
          >
            <section className={styles.savedMemoTop}>
              <h1 className={styles.memoTitle}>{props.title}</h1>
              <strong className={styles.memoContent}>{date}</strong>
            </section>
            <section className={styles.savedMemoBottom}>
              <strong className={styles.memoContent}>{props.content}</strong>
            </section>
          </section>
        </li>
      </section>
    </>
  );
};

export default SavedMemo;
