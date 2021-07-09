import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg';

import './styles.css';

const Pagination = () => {
    return(
        <div className="pagination-container">
            <ArrowIcon className="arrow-previous arrow-inactive"/>
            <div className="pagintion-item active">1</div>
            <div className="pagintion-item">2</div>
            <div className="pagintion-item">3</div>
            <div className="pagintion-item">4</div>
            <div className="pagintion-item">...</div>
            <div className="pagintion-item">12</div>
            <ArrowIcon className="arrow-next arrow-active"/>
        </div>
    );
};

export default Pagination;