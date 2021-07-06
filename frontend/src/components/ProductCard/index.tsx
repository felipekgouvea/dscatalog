import './styles.css';
import ProductImg from '../../assets/images/product-card.png';
import ProductPrice from '../ProductPrice';

const ProductCard = () => {
  return (
    <div className="product-card base-card">
      <div className="card-top-container">
        <img src={ProductImg} alt="Nome do Produto"/>            
      </div>
      <div className="card-bottom-container">
        <h6>Nome do Produto</h6>
        <ProductPrice/>
      </div>
    </div>
  );
};

export default ProductCard;
