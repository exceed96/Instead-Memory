import { useState } from "react";
import { useDispatch } from "react-redux";
import styles from "./WriteMemoButton.module.css";
import WriteMemoModal from "../WriteMemoModal/WriteMemoModal";
import { expansionMemoActions } from "../../../../store/expansionMemoState";

const WriteMemoButton = (props) => {
  const [writeMemoModalState, setWriteMemoModalstate] = useState(false);

  const dispatch = useDispatch();
  const InfoMemoCss = props.important
    ? styles.writeMemoButtonImportant
    : styles.writeMemoButton;
  const InfoMemoTitleCss = props.important
    ? styles.importantMemoTitle
    : styles.entireMemoTitle;
  const InfoMemoCountCss = props.important
    ? styles.importantMemoCount
    : styles.entireMemoCount;

  const writeMemoModalHandler = () => {
    setWriteMemoModalstate((prevState) => !prevState);
    dispatch(expansionMemoActions.setModalHandler());
  };

  return (
    <>
      {writeMemoModalState && (
        <WriteMemoModal
          closeModal={writeMemoModalHandler}
          important={props.important}
        />
      )}
      <button className={InfoMemoCss} onClick={writeMemoModalHandler}>
        <strong className={InfoMemoTitleCss}>{props.title}</strong>
        <strong className={InfoMemoCountCss} style={{ "font-size": "30px" }}>
          {props.count}
        </strong>
      </button>
    </>
  );
};

export default WriteMemoButton;
