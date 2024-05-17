import { useSelector, useDispatch } from "react-redux";
import styles from "./TrashComponent.module.css";
import axios from "axios";
import getAccessToken from "../../../store/accessTokenState";
import { mainActions } from "../../../store/mainState";
import { trashSelectDataStateAction } from "../../../store/trashSelectDataState";

const TrashComponent = () => {
  const dispatch = useDispatch();
  const receiveDeleteData = useSelector(
    (state) => state.deleteMemoState.receiveDeleteData
  );

  const selectData = useSelector(
    (state) => state.trashSelectDataState.selectData
  );

  const restoreDeletebuttonCss =
    selectData.length === 0 ? `${styles.restoreDeleteBtn}` : `${styles.active}`;

  const allDeleteButtonCss =
    receiveDeleteData.length === 0
      ? `${styles.allDeleteBtn}`
      : `${styles.active}`;
  const restoreMemo = async (evt) => {
    const restoreUrl = "https://api.insteadmemo.kr/v1/memo/restore";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const finalData = JSON.stringify(selectData);
    await axios({
      method: "put",
      url: restoreUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    dispatch(mainActions.setGetData());
    dispatch(trashSelectDataStateAction.selectData([]));
  };

  const restoreMemoHandler = async () => {
    try {
      await restoreMemo();
    } catch (error) {
      dispatch(getAccessToken(error, restoreMemo));
    }
  };

  const deleteMemo = async (evt) => {
    const deleteUrl = "https://api.insteadmemo.kr/v1/memo/delete";
    const header = {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("userToken")}`,
    };
    const finalData = JSON.stringify(selectData);
    await axios({
      method: "delete",
      url: deleteUrl,
      headers: header,
      withCredentials: true,
      data: finalData,
    });
    dispatch(mainActions.setGetData());
    dispatch(trashSelectDataStateAction.selectData([]));
  };

  const deleteMemoHandler = async () => {
    try {
      await deleteMemo();
    } catch (error) {
      dispatch(getAccessToken(error, deleteMemo));
    }
  };
  return (
    <section className={styles.trashSection}>
      <header className={styles.trashSectionHeader}>
        <section>
          <button onClick={deleteMemoHandler} className={allDeleteButtonCss}>
            전체삭제
          </button>
        </section>
        <section>
          <button
            onClick={restoreMemoHandler}
            className={restoreDeletebuttonCss}
          >
            복원
          </button>
          <button
            onClick={deleteMemoHandler}
            className={restoreDeletebuttonCss}
          >
            삭제
          </button>
        </section>
      </header>
      <main className={styles.trashSectionMain}>
        <ul className={styles.savedMemoContainer}>{receiveDeleteData}</ul>
      </main>
    </section>
  );
};

export default TrashComponent;
