import { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import styles from "./SavedMemo.module.css";
import WriteMemoModal from "../WriteMemoComponent/WriteMemoModal/WriteMemoModal";
import ExpansionMemo from "../ExpansionMemo/ExpansionMemo";
import { expansionMemoActions } from "../../../store/expansionMemoState";

const SavedMemo = (props) => {
  const [expansionMemo, setExpansionMemo] = useState(false);
  const dispatch = useDispatch();

  const expansionMemoHandler = () => {
    setExpansionMemo((prevState) => !prevState);
  };

  const openModal = () => {
    dispatch(expansionMemoActions.setModalHandler());
  };

  return (
    <>
      {expansionMemo && (
        <ExpansionMemo uuid={props.uuid} closeModal={expansionMemoHandler} />
      )}
      <section onClick={openModal}>
        <li className={styles.memo} onClick={expansionMemoHandler}>
          <section className={styles.savedMemoTop}>
            <h1 className={styles.memoTitle}>{props.title}</h1>
            <strong className={styles.memoContent}>FRIDAY</strong>
          </section>
          <section className={styles.savedMemoBottom}>{props.content}</section>
        </li>
      </section>
    </>
  );
};

export default SavedMemo;
