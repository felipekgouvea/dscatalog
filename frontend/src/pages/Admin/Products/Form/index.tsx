import { AxiosRequestConfig } from 'axios';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useHistory, useParams } from 'react-router-dom';
import { Product } from '../../../../types/product';
import { requestBackend } from '../../../../util/requests';
import Select from 'react-select';
import './styles.css';

type UrlParams = {
  productId: string;
};

const options = [
  { value: 'chocolate', label: 'Chocolate' },
  { value: 'strawberry', label: 'Strawberry' },
  { value: 'vanilla', label: 'Vanilla' },
];

const Form = () => {
  const history = useHistory();
  const { productId } = useParams<UrlParams>();
  const isEditing = productId !== 'create';

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<Product>();

  useEffect(() => {
    if (isEditing) {
      requestBackend({ url: `/products/${productId}` }).then((response) => {
        const product = response.data as Product;

        setValue('name', product.name);
        setValue('price', product.price);
        setValue('description', product.description);
        setValue('imgUrl', product.imgUrl);
        setValue('categories', product.categories);
      });
    }
  }, [productId, setValue, isEditing]);

  const onSubmit = (formData: Product) => {
    const data = {
      ...formData,
      imgUrl: isEditing
        ? formData.imgUrl
        : 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg',
      categories: isEditing ? formData.categories : [{ id: 1, name: '' }],
    };

    const config: AxiosRequestConfig = {
      method: isEditing ? 'PUT' : 'POST',
      url: isEditing ? `/products/${productId}` : '/products',
      data,
      withCredentials: true,
    };

    requestBackend(config).then(() => {
      history.push('/admin/products');
    });
  };

  const handleCancel = () => {
    history.push('/admin/products');
  };

  return (
    <div className="product-crud-container">
      <div className="base-card product-crud-form-card">
        <h1 className="product-crud-form-title">DADOS DO PRODUTO</h1>

        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="row product-crud-inputs-container">
            <div className="col-lg-6 product-crud-inputs-left-container">
              <div className="product-crud-input product-crud-others-inputs-container">
                <input
                  {...register('name', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.name ? 'is-invalid' : ''
                  }`}
                  placeholder="Nome do Produto"
                  name="name"
                />
                <div className="invalid-feedback d-block">
                  {errors.name?.message}
                </div>
              </div>

              <div className="product-crud-input product-crud-others-inputs-container">
                <Select 
                options={options}
                isMulti
                classNamePrefix="product-crud-select" />
              </div>

              <div className="">
                <input
                  {...register('price', {
                    required: 'Campo obrigatório',
                  })}
                  type="text"
                  className={`form-control base-input ${
                    errors.name ? 'is-invalid' : ''
                  }`}
                  placeholder="Preço"
                  name="price"
                />
                <div className="invalid-feedback d-block">
                  {errors.name?.message}
                </div>
              </div>
            </div>
            <div className="col-lg-6">
              <div>
                <textarea
                  rows={10}
                  {...register('description', {
                    required: 'Campo obrigatório',
                  })}
                  className={`form-control base-input h-auto ${
                    errors.name ? 'is-invalid' : ''
                  }`}
                  placeholder="Descrição"
                  name="description"
                ></textarea>
                <div className="invalid-feedback d-block">
                  {errors.description?.message}
                </div>
              </div>
            </div>
          </div>

          <div className="product-crud-buttons-container">
            <button
              className="btn btn-outline-danger product-crud-button"
              onClick={handleCancel}
            >
              CANCELAR
            </button>
            <button className="btn btn-primary product-crud-button text-white">
              SALVAR
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Form;
