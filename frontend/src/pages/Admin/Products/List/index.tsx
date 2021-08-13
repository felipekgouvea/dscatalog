import { AxiosRequestConfig } from 'axios';
import { useCallback, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Pagination from '../../../../components/Pagination';
import { Product } from '../../../../types/product';
import { SpringPage } from '../../../../types/vendor/spring';
import { requestBackend } from '../../../../util/requests';
import ProductCrudCard from '../ProductCrudCard';
import ProductFilter, {
  ProductFilterData,
} from '../../../../components/ProductFilter';
import './styles.css';

type ControlComponentsData = {
  activePage: number;
  filterData: ProductFilterData;
};

const List = () => {
  const [page, setPage] = useState<SpringPage<Product>>();
  const [controlComponentsData, setControlComponentsData] =
    useState<ControlComponentsData>({
      activePage: 0,
      filterData: { name: '', category: null },
    });

  const handlePageChange = (pageNumber: number) => {
    setControlComponentsData({
      activePage: pageNumber,
      filterData: controlComponentsData.filterData,
    });
  };

  const handleSubmitFilter = (data: ProductFilterData) => {
    setControlComponentsData({
      activePage: 0,
      filterData: data,
    });
  };

  const getProducts = useCallback(() => {
    const config: AxiosRequestConfig = {
      method: 'GET',
      url: '/products',
      params: {
        page: controlComponentsData.activePage,
        size: 3,
        name:controlComponentsData.filterData.name,
        categoryId: controlComponentsData.filterData.category?.id
      },
    };

    requestBackend(config).then((response) => {
      setPage(response.data);
    });
  }, [controlComponentsData]);

  useEffect(() => {
    getProducts();
  }, [getProducts]);

  return (
    <div className="product-crud-container">
      <div className="product-crud-bar-container">
        <Link to="/admin/products/create">
          <button className="btn btn-primary text-white btn-crud-add">
            ADICINAR
          </button>
        </Link>
        <ProductFilter onSubmitFilter={handleSubmitFilter} />
      </div>
      <div className="row">
        {page?.content.map((product) => (
          <div className="col-sm-6 col-md-12">
            <ProductCrudCard
              product={product}
              key={product.id}
              onDelete={getProducts}
            />
          </div>
        ))}
      </div>
      <Pagination
        pageCount={page ? page.totalPages : 0}
        range={3}
        onChange={handlePageChange}
        forcePage={page?.number}
      />
    </div>
  );
};

export default List;
