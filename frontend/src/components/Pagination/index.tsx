import ReactPaginate from 'react-paginate';
import { ReactComponent as ArrowIcon } from '../../assets/images/arrow.svg';

import './styles.css';

type Props = {
  forcePage?: number;
  pageCount: number;
  range: number;
  onChange?: (pageNumber: number) => void;
};

const Pagination = ({ pageCount, range, onChange, forcePage }: Props) => {
  return (
    <ReactPaginate
      pageCount={pageCount}
      pageRangeDisplayed={range}
      marginPagesDisplayed={1}
      containerClassName="pagination-container"
      pageLinkClassName="pagintion-item"
      breakClassName="pagintion-item"
      previousClassName="arrow-previous"
      nextClassName="arrow-next"
      forcePage={forcePage}
      activeLinkClassName="pagintion-link-active"
      disabledClassName="arrow-inactive"
      previousLabel={
        <div className="pagination-arrow-container">
          <ArrowIcon />
        </div>
      }
      nextLabel={
        <div className="pagination-arrow-container">
          <ArrowIcon />
        </div>
      }
      onPageChange={(items) => (onChange ? onChange(items.selected) : {})}
    />
  );
};

export default Pagination;
