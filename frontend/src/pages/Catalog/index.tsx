import ProductCard from '../../components/ProductCard';
import { Link } from 'react-router-dom';
import { Product } from '../../types/product';
import Pagination from '../../components/Pagination';
import { useEffect, useState } from 'react';
import { SpringPage } from '../../types/vendor/spring';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from '../../util/requests';
import '../Catalog/styles.css';
import CardLoader from './CardLoader';

const Catalog = () => {
  const [page, setPage] = useState<SpringPage<Product>>();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const params: AxiosRequestConfig = {
      method: 'GET',
      url: "/products",      
      params: {
        page: 0,
        size: 12,
      },
    };

    setIsLoading(true);
    requestBackend(params)
      .then((response) => {
        setPage(response.data);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, []);

  return (
    <div className="container my-4 catalog-container">
      <div className="row catalog-title-container">
        <h1>Catálogo de Produtos</h1>
      </div>
      <div className="row">
        {isLoading ? (
          <>
            <CardLoader />            
          </>

        ) : (
          page?.content.map((product) => (
            <div className="col-sm-6 col-lg-4 col-xl-3" key={product.id}>
              <Link to="/products/4">
                <ProductCard product={product} />
              </Link>
            </div>
          ))
        )}
      </div>
      <div className="row">{page ? <Pagination /> : ''}</div>
    </div>
  );
};

export default Catalog;