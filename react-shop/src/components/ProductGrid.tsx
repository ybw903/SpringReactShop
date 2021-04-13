import React from 'react';
import {
    Container,
    Col,
    Row
} from 'react-bootstrap';
import { Product } from '../types/products';
import ProductCard from './ProductCard';

type ProductGridProps = {
    productList: Product[],
    columnCount?: number,
}

const BOOTSTRAP_GRID_WIDTH = 12;

const ProductGrid =({productList, columnCount =4}: ProductGridProps) => {

    let productRows: Product[][] = [];
    let productColumns: Product[] = [];
    productList.forEach(product => {    
      productColumns.push(product);
      if (productColumns.length === columnCount) {
        productRows.push(productColumns);
        productColumns = [];
      }
    });

    productRows.push(productColumns);

    return(
        <Container>
          { productRows.map((productColumn, i) => {
        return (
          <Row key={i}>
            { productColumn.map((product, j) => {
              return (
              // gaurd against columnCount being set to 0 or a negative number
              <Col key={j} md={Math.ceil(BOOTSTRAP_GRID_WIDTH / (columnCount <= 0 ? 1 : columnCount))}>
                <ProductCard product={product} />
              </Col>);
            })}
          </Row>);
      })}
        </Container>
    )
}

export default ProductGrid;