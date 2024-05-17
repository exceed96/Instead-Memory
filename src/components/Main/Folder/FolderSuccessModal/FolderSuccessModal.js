import { useDispatch } from "react-redux";
import styles from "./FolderSuccessModal.module.css";
import ReactDom from "react-dom";
import { folderModalActions } from "../../../../store/folderModalState";

const Overlay = () => {
    return <section className={styles.modalOverlay}></section>;
}

const FolderSuccessModalContent = () => {
    const dispatch = useDispatch();

    const closeModal = () => {
        dispatch(folderModalActions.setFolderErrorCode(0));
    }
    return (
        <section className={styles.successModal}>
            <span>디렉토리 선택이 완료되었습니다.</span>
            <button className={styles.modalCloseButton} onClick={closeModal}>확인</button>
        </section>
    )
}

const FolderSuccessModal = () => {
    return (
        <>
            {ReactDom.createPortal(<Overlay/>, document.getElementById("overlay"))}
            {ReactDom.createPortal(<FolderSuccessModalContent />, document.getElementById("modalContent"))}
        </>
    )
}

export default FolderSuccessModal;
