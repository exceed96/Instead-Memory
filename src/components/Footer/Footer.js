import { useState } from "react";
import { useDispatch } from "react-redux";
import styles from "./Footer.module.css";
import { expansionMemoActions } from "../../store/expansionMemoState";
import WriteMemoModal from "../Main/WriteMemoComponent/WriteMemoModal/WriteMemoModal";
import plusIcon from "../../assets/Pluse.png";

const Footer = () => {
  const [writeMemoModalState, setWriteMemoModalstate] = useState(false);

  const dispatch = useDispatch();
  const writeMemoModalHandler = () => {
    setWriteMemoModalstate((prevState) => !prevState);
    dispatch(expansionMemoActions.setModalHandler());
  };

  return (
    <>
      {writeMemoModalState && (
        <WriteMemoModal closeModal={writeMemoModalHandler} />
      )}
      <section className={styles.footer}>
        <img src={plusIcon} onClick={writeMemoModalHandler} alt="add memo"></img>
      </section>
    </>
  );
};

export default Footer;
