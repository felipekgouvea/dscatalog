import { formatPrice } from '../../util/formatters';
import './styles.css';

type Propos = {
  price: number;
};

const ProductPrice = ({ price }: Propos) => {
  return (
    <div className="product-price-container">
      <span>R$</span>
      <h3>{formatPrice(price)}</h3>
    </div>
  );
};

export default ProductPrice;
