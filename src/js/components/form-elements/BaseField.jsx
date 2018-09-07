import _ from 'lodash';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Field } from 'react-final-form';

import { renderField } from '../../utils/form-utils';

class BaseField extends Component {
  constructor(props) {
    super(props);

    this.state = {
      touched: false,
    };

    this.renderInput = this.renderInput.bind(this);
  }

  shouldComponentUpdate(nextProps, nextState) {
    if (this.state.touched !== nextState.touched) {
      return true;
    }

    return !_.isEqualWith(this.props, nextProps, (objValue, othValue) => {
      if (typeof objValue === 'function' || typeof othValue === 'function') {
        return true;
      }

      return undefined;
    });
  }

  renderInput(input, attr) {
    const onChange = (value) => {
      if (attr.onChange) {
        attr.onChange(value);
      }

      input.onChange(value);
    };

    const onBlur = (value) => {
      if (attr.onBlur) {
        attr.onBlur(value);
      }

      this.setState({ touched: true });
    };

    const attributes = {
      ...attr,
      value: input.value,
      onChange,
      onBlur,
    };

    return this.props.renderInput(attributes);
  }

  render() {
    const {
      fieldName,
      fieldConfig: { label, getDynamicAttr, attributes = {} },
      renderInput,
      arrayField,
      fieldValue,
      fieldPreview,
      ...otherProps
    } = this.props;
    const dynamicAttr = getDynamicAttr ? getDynamicAttr({ ...otherProps, fieldValue }) : {};

    if (fieldPreview) {
      return (
        <div className="form-group my-0 ">
          {renderInput({
            ...attributes, ...dynamicAttr, value: fieldValue, disabled: true,
          })}
        </div>
      );
    }

    return (
      <Field
        name={fieldName}
        component={renderField}
        renderInput={this.renderInput}
        attributes={{
          ...attributes,
          ...dynamicAttr,
        }}
        label={label}
        touched={this.state.touched}
        arrayField={arrayField}
      />
    );
  }
}

export default BaseField;

BaseField.propTypes = {
  fieldName: PropTypes.string.isRequired,
  fieldConfig: PropTypes.shape({
    getDynamicAttr: PropTypes.func,
  }).isRequired,
  renderInput: PropTypes.func.isRequired,
  arrayField: PropTypes.bool,
  fieldPreview: PropTypes.bool,
  fieldValue: PropTypes.oneOfType([PropTypes.string,
    PropTypes.shape({}), PropTypes.any]),
};

BaseField.defaultProps = {
  arrayField: false,
  fieldPreview: false,
  fieldValue: undefined,
};
