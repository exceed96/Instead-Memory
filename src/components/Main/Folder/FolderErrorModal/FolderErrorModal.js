import styles from "./FolderErrorModal.module.css";
import ReactDom from "react-dom";
import { useDispatch } from "react-redux";
import { folderModalActions } from "../../../../store/folderModalState";

const Overlay = () => {
  return <section className={styles.modalOverlay}></section>;
};

const ExpansionMemoContent = (props) => {
  const dispatch = useDispatch();

  const closeModal = () => {
    dispatch(folderModalActions.setFolderErrorCode(0));
  };

  return (
    <section className={styles.errorModal}>
      <span>폴더 명이 중복되어 생성이 불가합니다.</span>
      <button onClick={closeModal} className={styles.modalCloseButton}>
        확인
      </button>
    </section>
  );
};

const FolderErrorModal = () => {
  return (
    <>
      {ReactDom.createPortal(<Overlay />, document.getElementById("overlay"))}
      {ReactDom.createPortal(
        <ExpansionMemoContent />,
        document.getElementById("modalContent")
      )}
    </>
  );
};

export default FolderErrorModal;
