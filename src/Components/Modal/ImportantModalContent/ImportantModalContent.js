import React, { useEffect, useState, useCallback } from "react";
import styles from "./ImportantModalContent.module.css";
import ReactDOM, { createPortal } from "react-dom";
import SavedMemo from "../../SavedMemo/SavedMemo";
import axios from "axios";

const Overlay = (props) => {
  return (
    <section
      className={styles.importantMemoModalOverlay}
      onClick={props.modalOffHandler}
    ></section>
  );
};

const ModalContent = (props) => {
  const [importMemo, setImportMemo] = useState([]);
  const [httpFlag, setHttpFlag] = useState(false);
  const getUrl =
    "http://ec2-3-34-168-144.ap-northeast-2.compute.amazonaws.com:8080/v1/memo/find";
  const header = {
    Authorization: `Bearer ${localStorage.getItem("userToken")}`,
  };

  useEffect(() => {
    const receiveData = async () => {
      const data = await axios({
        method: "get",
        url: getUrl,
        headers: header,
      });
      const parseData = data.data.data.filter((memo) => {
        if (memo.important) {
          return memo;
        }
      });
      setImportMemo([...parseData]);
    };
    receiveData();
  }, []);
  return (
    <section className={styles.importantMemoModalContent}>
      <section className={styles.importantMemoSection}>
        {importMemo.map((memo) => (
          <SavedMemo title={memo.title} content={memo.content} />
        ))}
      </section>
    </section>
  );
};

const ImportantModalContent = (props) => {
  return (
    <React.Fragment>
      {ReactDOM.createPortal(
        <Overlay modalOffHandler={props.modalOffHandler} />,
        document.getElementById("overlay")
      )}
      {ReactDOM.createPortal(
        <ModalContent />,
        document.getElementById("modalContent")
      )}
    </React.Fragment>
  );
};

export default React.memo(ImportantModalContent);
