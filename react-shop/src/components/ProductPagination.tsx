import React, { useState } from 'react';
import { Pagination } from 'react-bootstrap';

type pageProps= {
    page: any;
    setPage:any;
    pageSize:number;
}

const ProductPagination = ({page, setPage, pageSize}:pageProps) => {

    const [pageArr, setPageArr] = useState(Array.from({length:pageSize}).map((v,i)=>i));
    
    const pageBefore = () => { 
        const changePageNum = (page.number - pageSize<0?0:page.number-pageSize);
        setPage({ ...page, number: changePageNum });
        
        console.log(changePageNum);
        if(pageSize!==pageArr.length || page.number-pageSize) {
            setPageArr([...Array(pageSize)].map((v,i)=>i+changePageNum))
        } else {
            setPageArr(pageArr.map((v,i)=>v-5));
        }
        console.log(pageArr);
     }
    const pageNext = () => {
        const changePageNum = (page.number + pageSize >page.totalPages ? page.totalPages-1:page.number + pageSize);
        setPage({ ...page, number: changePageNum });
        if(pageSize>page.totalPages-changePageNum){
            setPageArr([...Array(page.totalPages-changePageNum)].map((v,i)=>i+changePageNum));
        } else {
            setPageArr(pageArr.map((v,i)=>v+pageSize));
        }
        console.log(pageArr);
    }

    return(
        <Pagination className="justify-content-center">
                <Pagination.First onClick={pageBefore} />
                {
                    pageArr.map(
                        (v, i) => {
                            if (v === page.number) return (<Pagination.Item active onClick={() => setPage({ ...page, number: v })}>{v + 1}</Pagination.Item>);
                            return (<Pagination.Item onClick={() => setPage({ ...page, number: v })}>{v + 1}</Pagination.Item>);
                        })
                }
                <Pagination.Last onClick={pageNext} />
        </Pagination>
    );
}

export default ProductPagination;